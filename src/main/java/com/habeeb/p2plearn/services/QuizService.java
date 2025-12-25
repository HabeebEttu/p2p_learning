package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.*;
import com.habeeb.p2plearn.models.QuestionCategory;
import com.habeeb.p2plearn.models.QuizAttempt;

import java.util.List;

public interface QuizService {
    QuizResponseDto createQuiz(QuizCreationDto dto);
    List<QuizResponseDto> getAllQuizzes();
    QuizDetailDto getQuizById(Long quizId);
    QuizResponseDto updateQuiz(Long quizId, QuizCreationDto dto);
    void deleteQuiz(Long quizId);
    QuizResultDto submitQuiz(Long userId, QuizSubmissionDto submission);
    List<QuizResponseDto> getQuizzesByCategory(QuestionCategory category);
    List<QuizAttempt> getUserAttempts(Long userId);
}