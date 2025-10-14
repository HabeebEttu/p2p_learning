package com.habeeb.p2plearn.dto;

import java.util.List;

public record QuizSubmissionResponse(
        int score,
        int xpGained,
        List<QuestionCreationRequestDto> questions
) {
}
