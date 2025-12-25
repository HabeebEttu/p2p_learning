package com.habeeb.p2plearn.dto;

import java.util.Map;

public record QuizSubmissionDto(
        Long quizId,
        Map<Long, Character> answers // questionId -> answer
) {}