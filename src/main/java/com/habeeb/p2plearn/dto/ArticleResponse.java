package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(
        String slug,
        String title,
        Long likes,
        Long dislikes,
        List<Comment> comments,
        String content,
        LocalDateTime updatedAt,
        String author,
        LocalDateTime createdAt
) {
}
