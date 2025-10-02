package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.QuizDto;
import com.habeeb.p2plearn.dto.QuizRequest;

public interface QuizService {
    QuizDto createQuiz(QuizRequest quizRequest);
}