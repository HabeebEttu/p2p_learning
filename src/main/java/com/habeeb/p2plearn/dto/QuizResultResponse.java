package com.habeeb.p2plearn.dto;

import java.util.List;

public record QuizResultResponse(
        Long attemptId,
        Long quizId,
        String quizTitle,
        Integer score,
        Integer maxScore,
        Integer percentageScore,
        Boolean passed,
        Integer xpAwarded,
        Integer correctAnswers,
        Integer totalQuestions,
        Integer timeTaken,
        List<QuestionResultResponse> questionResults
) {
}
