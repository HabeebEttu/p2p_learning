package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.QuestionCategory;

import java.util.Map;


public record QuestionCreationRequestDto(
     String questionText,
     Map<Character,String> options,
     Character answer,
     QuestionCategory category,
     Level difficulty
){

}
