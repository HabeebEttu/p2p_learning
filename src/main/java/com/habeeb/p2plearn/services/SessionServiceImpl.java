package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.models.Session;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.SessionRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class SessionServiceImpl implements SessionService{
    private final SessionRepository sessionRepository;
    private static final int MAX_SESSIONS_PER_USER = 5;
    private static final int SESSION_DURATION_HOURS = 24*7;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    @Transactional
    public Session createSession(User user, String token, HttpServletRequest request) {
        long activeSessionCount = sessionRepository.countByUserAndIsActiveTrue(user);
        if(activeSessionCount >= MAX_SESSIONS_PER_USER){
            List<Session> userSessions = sessionRepository.findByUserAndIsActiveTrue(user);
            Session oldestSession = userSessions.stream()
                    .min((s1, s2) -> s1.getCreatedAt().compareTo(s2.getCreatedAt()))
                    .orElse(null);
            if(oldestSession != null){
                sessionRepository.delete(oldestSession);
            }
        }
        Session session = Session.builder().user(user).token(token).createdAt(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusHours(SESSION_DURATION_HOURS))
                .userAgent(request.getHeader("User-Agent"))
                .isActive(true)
                .lastAccessedAt(LocalDateTime.now())
                .ipAddress(getClientIp(request))
                .build();

        return sessionRepository.save(session);
    }
    private String getClientIp( HttpServletRequest request){
        String xfHeader = request.getHeader("X-Forwarded-For");
        if(xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
    @Override
    public boolean isValidSession(String token) {
        Optional<Session> session = sessionRepository.findByToken(token);
        if(session.isEmpty()){
            return false;
        }
        if(session.get().getExpiryDate().isBefore(LocalDateTime.now())){
            session.get().setActive(false);
            return false;
        }
        sessionRepository.save(session.get());
        return true;
    }

    @Override
    @Transactional
    public void invalidateSession(String token) {
        Optional<Session> session = sessionRepository.findByToken(token);
        session.ifPresent(s -> {
            s.setActive(false);
            sessionRepository.save(s);
        });
    }

    @Transactional
    @Override
    public void invalidateAllUserSessions(User user) {
        List<Session> userSessions = sessionRepository.findByUserAndIsActiveTrue(user);
        if(!userSessions.isEmpty()){
            userSessions.forEach(s->s.setActive(false));
        }
        sessionRepository.saveAll(userSessions);
    }

    @Override
    @Transactional
    public void cleanupExpiredSessions() {
        sessionRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }

    @Override
    public List<Session> getActiveSessions(User user) {
        return sessionRepository.findByUserAndIsActiveTrue(user);
    }
}
