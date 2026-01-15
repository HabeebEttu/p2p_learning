package com.habeeb.p2plearn.dto;

public record QuestionResultResponse(
        Long questionId,
        String questionText,
        Integer selectedAnswer,
        Integer correctAnswer,
        Boolean isCorrect,
        Integer pointsAwarded,
        Integer maxPoints
) {
}