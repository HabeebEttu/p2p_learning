package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.ArticleCategory;
import java.util.List;

public record QuizPost(
        String title,
        String description,
        ArticleCategory category,
        Integer timeLimit,
        Integer passingScore,
        Integer xpReward,
        List<QuestionPost> questions
) {}