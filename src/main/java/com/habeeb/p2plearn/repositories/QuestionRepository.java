package com.habeeb.p2plearn.repositories;

import com.habeeb.p2plearn.models.Level;
import com.habeeb.p2plearn.models.Question;
import com.habeeb.p2plearn.models.QuestionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    Optional<Question> findByCategory(QuestionCategory category);
    Optional<Question> findByDifficulty(Level difficulty);
}
