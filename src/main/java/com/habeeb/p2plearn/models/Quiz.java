package com.habeeb.p2plearn.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Column(nullable = false)
    private Integer timeLimit; // in minutes

    @Column(nullable = false)
    private Integer passingScore; // percentage

    @Column(nullable = false)
    private Integer xpReward;
    @Enumerated(EnumType.STRING)
    private Level difficulty;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<Question> questions = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Helper method to add question
    public void addQuestion(Question question) {
        questions.add(question);
        question.setQuiz(this);
    }

    // Helper method to remove question
    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setQuiz(null);
    }
}