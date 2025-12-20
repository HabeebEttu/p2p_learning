package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.AdminDashboardResponse;
import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.dto.UserDto;
import com.habeeb.p2plearn.models.Quiz;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{
    private final UserService userService;
    private final ArticleService articleService;
    private final  QuizService quizService;

    public AdminServiceImpl(UserService userService, ArticleService articleService, QuizService quizService) {
        this.userService = userService;
        this.articleService = articleService;
        this.quizService = quizService;
    }

    @Override
    public void addUser(Long userId) {

    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public void blockUser(Long userId) {

    }

    @Override
    public void editArticles(Long articleId) {

    }

    @Override
    public void deleteArticles(Long articleId) {

    }

    @Override
    public AdminDashboardResponse getDashboardData() {
        List<UserDto> users = userService.getAllUsers();
        List<ArticleResponse> articles = articleService.getAllArticles();
        List
    }
}
