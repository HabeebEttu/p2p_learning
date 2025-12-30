package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.ArticleCategory;
import com.habeeb.p2plearn.models.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(
        Long id,
        Long userId,
        String coverImageUrl,
        String title,
        ArticleCategory category,
        long likes,
        long dislikes,
        List<Comment> comments,
        String bodyMarkdown,
        String bodyHtml,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}