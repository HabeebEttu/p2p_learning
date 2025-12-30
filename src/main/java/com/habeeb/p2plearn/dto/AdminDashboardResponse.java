package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Article;
import com.habeeb.p2plearn.models.Quiz;
import com.habeeb.p2plearn.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class AdminDashboardResponse {
    private List<UserDto> users;
    private List<ArticleResponse> articles;
    private List<QuizResponseDto> quizzes;

    public AdminDashboardResponse(List<UserDto> users, List<ArticleResponse> articles, List<QuizResponseDto> quizzes) {
        this.users = users;
        this.articles = articles;
        this.quizzes = quizzes;
    }
}
