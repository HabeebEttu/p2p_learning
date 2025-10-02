package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.QuestionCategory;
import lombok.Data;

@Data
public class QuizRequest {
    private int numQuestions;
    private QuestionCategory category;
    private Level difficulty;
}