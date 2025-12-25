package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.QuestionCategory;

import java.util.List;

public record QuizCreationDto(
        String title,
        String description,
        QuestionCategory category,
        Level difficulty,
        Integer xpReward,
        Integer timeLimit,
        List<Long> questionIds
) {}