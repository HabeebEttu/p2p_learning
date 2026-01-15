package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.*;
import com.habeeb.p2plearn.models.QuestionCategory;
import com.habeeb.p2plearn.models.QuizAttempt;
import org.springframework.data.domain.Page;

import java.util.List;

public interface QuizService {
   QuizResponse createQuiz(QuizPost quizPost, Long userId);
   Page<QuizResponse> getAllQuizzes(int page,int size);
    QuizResponse getQuizById(Long id, boolean includeAnswers);
    void deleteQuiz(Long id);
}