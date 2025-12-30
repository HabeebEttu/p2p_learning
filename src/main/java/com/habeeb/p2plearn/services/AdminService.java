package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.AdminDashboardResponse;
import com.habeeb.p2plearn.dto.ArticlePost;
import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.models.Article;

public interface AdminService {
void addUser(Long userId);
void deleteUser(Long userId);
void blockUser(Long userId);
void editArticles(Long articleId);
void deleteArticles(Long articleId);
ArticleResponse createArticle(ArticlePost a,String coverImageUrl);
AdminDashboardResponse getDashboardData();
}