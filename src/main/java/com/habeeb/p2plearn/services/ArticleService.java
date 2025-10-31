package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.dto.ArticlePost;
import com.habeeb.p2plearn.dto.CommentPost;
import com.habeeb.p2plearn.dto.CommentResponse;
import com.habeeb.p2plearn.models.Article;
import com.habeeb.p2plearn.models.Comment;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.ArticleRepository;
import com.habeeb.p2plearn.repositories.CommentRepository;
import com.habeeb.p2plearn.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ArticleService(ArticleRepository articleRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }
    private ArticleResponse convertToDto(Article a){
        return new ArticleResponse(a.getSlug(),a.getTitle(),a.getLikes(),a.getDislikes(),a.getComments(),a.getBody(),a.getUpdatedAt(),a.getUser().getUsername(),a.getCreatedAt());
    }
    public ArticleResponse getArticleBySlug(String slug) {
        Article a = articleRepository.findBySlug(slug).orElseThrow();

    return convertToDto(a);

    }

    public void createArticle(ArticlePost articleDto) {
        Article a  = new Article();
        User user = userRepository.findById(articleDto.userId()).orElse(null);
        a.setBody(articleDto.body());
        a.setTitle(articleDto.title());
        a.setUser(user);

        articleRepository.save(a);
    }

    public void updateArticle(String slug, ArticlePost articlePost) {
        Article a = articleRepository.findBySlug(slug).orElse(null);
        if(a!=null){
            a.setTitle(articlePost.title());
            a.setBody(articlePost.body());
            a.setCoverImgUrl(articlePost.coverImageUrl());
            articleRepository.save(a);
        }
    }

    public void deleteArticle(String slug) {
        articleRepository.deleteBySlug(slug);
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream().map(this::convertToDto).toList();
    }


    public List<ArticleResponse> getAllByUserId(Long userId) {
        return articleRepository.findByUserId(userId).stream().map(this::convertToDto).toList();
    }

    public List<ArticleResponse> getAllByDate(Integer limit) {
        if(limit == null) {
            return articleRepository.findAll(Sort.by("createdAt")).stream().map(this::convertToDto).toList();
        }return articleRepository.findAll(Sort.by("createdAt")).stream().map(this::convertToDto).limit(limit).toList();
    }

    public List<ArticleResponse> searchArticles(String query) {
        return articleRepository.findByTitleContainingIgnoreCase(query).stream().map(this::convertToDto).toList();
    }

    public void commentArticle(String slug, CommentPost commentDto) {
        Comment comment = new Comment();
        Optional<Article> a  =  articleRepository.findBySlug(slug);
        a.ifPresent(comment::setArticle);
        Optional<User> u = userRepository.findByUsername(commentDto.username());
        u.ifPresent(comment::setUser);
        comment.setContent(commentDto.commentText());
        commentRepository.save(comment);
    }


    public List<CommentResponse> getArticleComments(String slug) {
        List<Comment> comments =commentRepository.findByArticleSlug(slug);

        return comments.stream().map((c)->new CommentResponse(
                c.getUser().getUsername(),
                c.getArticle().getSlug(),
                c.getArticle().getBody(),
                c.getArticle().getCreatedAt(),
                c.getArticle().getUpdatedAt()
        )).toList();
    }
}
