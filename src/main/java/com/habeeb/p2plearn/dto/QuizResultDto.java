package com.habeeb.p2plearn.dto;

public record QuizResultDto(
        Integer score,
        Integer totalQuestions,
        Integer xpEarned,
        Boolean passed,
        Double percentage
) {}