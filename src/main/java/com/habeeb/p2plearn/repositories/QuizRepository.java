package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.QuestionCategory;
import com.habeeb.p2plearn.models.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz , Long> {
    Page<Quiz> findByCategory(QuestionCategory category, Pageable pageable);
    Page<Quiz> findByDifficulty(Level difficulty, Pageable pageable);
    Page<Quiz> findByCategoryAndDifficulty(QuestionCategory category, Level difficulty, Pageable pageable);
    Page<Quiz> findAll(Pageable pageable);
}
