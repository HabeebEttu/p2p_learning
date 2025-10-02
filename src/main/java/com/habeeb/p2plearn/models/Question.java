package com.habeeb.p2plearn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String questionText;

    @ElementCollection
    @CollectionTable(
            name = "question_options",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @MapKeyColumn(name = "option_key")
    @Column(name = "option_value")
    private Map<Character, String> options = new HashMap<>();
    @Column(name = "answer")
    @NotNull
    private Character answer;
    @NotNull
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Level difficulty;
}
