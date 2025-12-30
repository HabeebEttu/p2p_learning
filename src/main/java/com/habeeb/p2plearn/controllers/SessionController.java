package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.models.Session;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.services.AuthService;
import com.habeeb.p2plearn.services.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final AuthService authService;

    public SessionController(SessionService sessionService, AuthService authService) {
        this.sessionService = sessionService;
        this.authService = authService;
    }

    @GetMapping("/active")
    public ResponseEntity<List<Session>> getActiveSessions() {
        User currentUser = authService.getCurrentUser();
        List<Session> sessions = sessionService.getActiveSessions(currentUser);
        return ResponseEntity.ok(sessions);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<String> revokeSession(@PathVariable Long sessionId) {
        // Implementation to revoke a specific session
        return ResponseEntity.ok("Session revoked");
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> revokeAllSessions() {
        User currentUser = authService.getCurrentUser();
        sessionService.invalidateAllUserSessions(currentUser);
        return ResponseEntity.ok("All sessions revoked");
    }
}