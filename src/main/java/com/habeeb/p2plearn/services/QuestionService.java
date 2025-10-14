package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.QuestionCreationRequestDto;

import java.util.List;

public interface QuestionService {

    String createQuestion(QuestionCreationRequestDto question);

    String updateQuestion(QuestionCreationRequestDto question, Long questionId);

    String deleteQuestion(Long questionId);

    List<QuestionCreationRequestDto> getAllQuestions();


    void createQuestions(List<QuestionCreationRequestDto> questions);
}
