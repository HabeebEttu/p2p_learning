package com.habeeb.p2plearn.config;

import com.habeeb.p2plearn.services.SessionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {

    private final SessionService sessionService;

    public ScheduledTasks(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void cleanupExpiredSessions() {
        sessionService.cleanupExpiredSessions();
        System.out.println("Cleaned up expired sessions at: " + java.time.LocalDateTime.now());
    }
}