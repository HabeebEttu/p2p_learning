package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.QuestionCategory;

import java.util.List;

public record QuizDto(
        List<QuestionCreationRequestDto> questions,
        QuestionCategory category
){

}
