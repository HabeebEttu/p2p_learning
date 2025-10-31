package com.habeeb.p2plearn.dto;

import java.time.LocalDateTime;

public record CommentResponse (
        String username,
        String slug,
        String content,
        LocalDateTime commentedAt,
        LocalDateTime updatedAt
){
}
