package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.QuestionCategory;
import lombok.Data;

import java.util.List;
import java.util.Map;


public record QuestionDto (
     String questionText,
     Map<Character,String> options,
     Character answer,
     QuestionCategory category,
     Level difficulty
){

}
