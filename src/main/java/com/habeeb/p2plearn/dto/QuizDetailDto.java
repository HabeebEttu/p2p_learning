package com.habeeb.p2plearn.dto;

import java.util.List;

public record QuizDetailDto(
        Long id,
        String title,
        String description,
        Integer xpReward,
        Integer timeLimit,
        List<QuestionCreationRequestDto> questions
) {}