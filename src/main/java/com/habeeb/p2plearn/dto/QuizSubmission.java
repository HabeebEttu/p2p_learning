package com.habeeb.p2plearn.dto;

import java.util.List;

public record QuizSubmission(
    Long userId,
    List<QuestionCreationRequestDto> questions,
    List<Character> answers

) {
}
