package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.*;
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
    private final UserRepository userRepository;


    public AdminServiceImpl(UserService userService, ArticleService articleService, QuizService quizService, UserRepository userRepository) {
        this.userService = userService;
        this.articleService = articleService;
        this.quizService = quizService;
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(Long userId) {

    }

    @Override
    public void deleteUser(Long userId) {
        User u = userRepository.findById(userId).orElseThrow(()->new RuntimeException("user not found"));
        userRepository.delete(u);

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
    public ArticleResponse createArticle(ArticlePost a,String coverImageUrl) {
        return articleService.createArticle(a,coverImageUrl);
    }

    @Override
    public AdminDashboardResponse getDashboardData() {
        List<UserDto> users = userService.getAllUsers();
        List<ArticleResponse> articles = articleService.getAllArticles();
        List<QuizResponseDto> quizzes = quizService.getAllQuizzes();
        return new AdminDashboardResponse(users , articles , quizzes);
    }
}
