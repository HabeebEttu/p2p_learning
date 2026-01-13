package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.QuestionType;
import java.util.List;

public record QuestionResponse(
        Long id,
        String questionText,
        QuestionType type,
        Integer points,
        List<OptionResponse> options,
        Integer correctAnswerIndex // Only include for admin/results
) {}