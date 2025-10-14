package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.QuizDto;
import com.habeeb.p2plearn.dto.QuizRequest;
import com.habeeb.p2plearn.dto.QuizSubmission;

public interface QuizService {
    QuizDto createQuiz(QuizRequest quizRequest);

    Integer submitQuiz(Long userId, QuizSubmission quizSubmission);
}