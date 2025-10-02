package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.QuestionCategory;
import lombok.Data;

import java.util.List;

public record QuizDto(
        List<QuestionDto> questions,
        QuestionCategory category
){

}
