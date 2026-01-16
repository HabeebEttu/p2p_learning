package com.habeeb.p2plearn.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type; // MULTIPLE_CHOICE, TRUE_FALSE, etc.

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private Integer correctAnswerIndex;

    // IMPORTANT: Add cascade and orphanRemoval
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("optionIndex ASC")
    private List<QuestionOption> options = new ArrayList<>();

    // Helper method to add option
    public void addOption(QuestionOption option) {
        options.add(option);
        option.setQuestion(this);
    }

    // Helper method to remove option
    public void removeOption(QuestionOption option) {
        options.remove(option);
        option.setQuestion(null);
    }
}