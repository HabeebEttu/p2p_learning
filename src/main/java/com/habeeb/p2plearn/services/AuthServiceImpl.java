package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.AuthResponse;
import com.habeeb.p2plearn.dto.LoginRequest;
import com.habeeb.p2plearn.dto.RegisterRequest;
import com.habeeb.p2plearn.exceptions.UserAlreadyExistsException;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.models.Rank;
import com.habeeb.p2plearn.models.Session;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.ProfileRepository;
import com.habeeb.p2plearn.repositories.UserRepository;
import com.habeeb.p2plearn.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final SessionServiceImpl sessionService;

    public AuthServiceImpl(AuthenticationManager authManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder, ProfileRepository profileRepository, UserService userService, SessionServiceImpl sessionService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        User userEntity = userRepository.findByUsername(user.getUsername()).get();
        String token = jwtUtil.generateToken(userEntity);


        Session session = sessionService.createSession(userEntity, token, httpRequest);

        return new AuthResponse(token, userEntity);
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest, HttpServletRequest request) {
        // Check if user exists
        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new RuntimeException("Email already exists");
        }

        // Create user
        User user = User.builder()
                .username(registerRequest.username())
                .email(registerRequest.email())
                .hashPassword(passwordEncoder.encode(registerRequest.password()))
                .build();

        // Create profile
        Profile profile = Profile.builder()
                .user(user)
                .xp(0)
                .rank(Rank.NOVICE)
                .build();

        user.setProfile(profile);
        user = userRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(user);

        // ✅ Create session
        Session session = sessionService.createSession(user, token, request);

        return new AuthResponse(token, user);
    }
    @Override
    @Transactional
    public void logout(String token) {
        sessionService.invalidateSession(token);
    }
    @Override
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userService.findByUsername(userName);
    }
}
