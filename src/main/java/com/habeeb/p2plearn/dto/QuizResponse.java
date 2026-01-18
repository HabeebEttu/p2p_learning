package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.ArticleCategory;
import com.habeeb.p2plearn.models.QuestionCategory;

import java.time.LocalDateTime;
import java.util.List;

public record QuizResponse(
        Long id,
        String creatorUsername,
        String title,
        String description,
        QuestionCategory category,
        Integer timeLimit,
        Integer passingScore,
        Integer xpReward,
        Integer questionCount,
        Long createdById,
        String createdByUsername,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<QuestionResponse> questions
) {}