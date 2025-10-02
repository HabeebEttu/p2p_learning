package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.QuestionDto;

import java.util.List;

public interface QuestionService {

    String createQuestion(QuestionDto question);

    String updateQuestion(QuestionDto question, Long questionId);

    String deleteQuestion(Long questionId);

    List<QuestionDto> getAllQuestions();


    void createQuestions(List<QuestionDto> questions);
}
