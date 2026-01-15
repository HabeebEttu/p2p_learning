package com.habeeb.p2plearn.dto;

import java.util.Map;

public record QuizSubmission(
        Long quizId,
        Map<Long, Integer> answers
) {
}
