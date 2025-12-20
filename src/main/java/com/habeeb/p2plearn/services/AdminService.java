package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.AdminDashboardResponse;

public interface AdminService {
void addUser(Long userId);
void deleteUser(Long userId);
void blockUser(Long userId);
void editArticles(Long articleId);
void deleteArticles(Long articleId);
AdminDashboardResponse getDashboardData();
}