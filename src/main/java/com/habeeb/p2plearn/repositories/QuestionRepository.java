package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.Question;
import com.habeeb.p2plearn.models.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategoryAndDifficulty(QuestionCategory category, Level difficulty);
}