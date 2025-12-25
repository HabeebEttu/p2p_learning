package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.QuestionCategory;

import java.time.LocalDateTime;
import java.util.List;

public record QuizResponseDto(
        Long id,
        String title,
        String description,
        QuestionCategory category,
        Level difficulty,
        Integer xpReward,
        Integer timeLimit,
        Integer totalQuestions,
        LocalDateTime createdAt
) {}