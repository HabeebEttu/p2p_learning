package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.QuestionCategory;
import com.habeeb.p2plearn.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz , Long> {
    List<Quiz> findByCategory(QuestionCategory category);
    List<Quiz> findByDifficulty(Level difficulty);
    List<Quiz> findByCategoryAndDifficulty(QuestionCategory category, Level difficulty);
}
