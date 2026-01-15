package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {
    List<QuizAttempt> findByUserId(Long userId);
    List<QuizAttempt> findByQuizId(Long quizId);
    boolean existsByUserIdAndQuizId(Long userId, Long quizId);
}