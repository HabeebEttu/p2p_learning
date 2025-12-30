package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.models.Session;
import com.habeeb.p2plearn.models.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SessionService {
Session createSession(User user, String token, HttpServletRequest request);
boolean isValidSession(String token);
void invalidateSession(String token);
void invalidateAllUserSessions(User user);
void cleanupExpiredSessions();
List<Session> getActiveSessions(User user);
}
