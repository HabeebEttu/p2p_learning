package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.QuestionType;
import java.util.List;

public record QuestionPost(
        String questionText,
        QuestionType type,
        Integer points,
        List<String> options,
        Integer correctAnswerIndex
) {}