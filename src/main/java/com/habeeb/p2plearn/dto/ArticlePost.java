package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.ArticleCategory;

public record ArticlePost(
        Long userId,
        String title,
        ArticleCategory category,
        String body
){

}
