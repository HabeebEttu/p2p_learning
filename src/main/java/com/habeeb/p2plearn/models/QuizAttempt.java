package com.habeeb.p2plearn.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "quiz_attempts")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ElementCollection
    @CollectionTable(
            name = "quiz_attempt_answers",
            joinColumns = @JoinColumn(name = "attempt_id")
    )
    @MapKeyColumn(name = "question_id")
    @Column(name = "answer")
    private Map<Long, Character> userAnswers = new HashMap<>();

    private Integer score;

    private Integer totalQuestions;

    private Integer xpEarned;

    private LocalDateTime completedAt;

    private Boolean passed;

    @PrePersist
    protected void onCreate() {
        completedAt = LocalDateTime.now();
    }
}