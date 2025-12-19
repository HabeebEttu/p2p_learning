package com.habeeb.p2plearn.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="quizzes")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany()
    private List<Question> questions;
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

}
