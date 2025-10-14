package com.habeeb.p2plearn.dto;

public record ArticlePost(
        Long userId,
        String coverImageUrl,
        String title,
        String body
){

}
