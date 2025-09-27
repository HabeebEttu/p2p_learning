package com.habeeb.p2plearn.dto;

import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdAt
) {
}
