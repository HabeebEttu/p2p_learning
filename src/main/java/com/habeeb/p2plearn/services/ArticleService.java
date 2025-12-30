package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.dto.ArticlePost;
import com.habeeb.p2plearn.dto.CommentPost;
import com.habeeb.p2plearn.dto.CommentResponse;
import com.habeeb.p2plearn.models.Article;
import com.habeeb.p2plearn.models.ArticleCategory;
import com.habeeb.p2plearn.models.Comment;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.ArticleRepository;
import com.habeeb.p2plearn.repositories.CommentRepository;
import com.habeeb.p2plearn.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final MarkdownService markdownService;

    private ArticleResponse convertToDto(Article a) {
        return new ArticleResponse(
                a.getId(),
                a.getUser().getId(),
                a.getCoverImgUrl(),
                a.getTitle(),
                a.getCategory(),
                a.getLikes(),
                a.getDislikes(),
                a.getComments(),
                a.getBodyMarkdown(),
                a.getBodyHtml(),
                a.getCreatedAt(),
                a.getUpdatedAt()
        );
    }

    public ArticleResponse getArticleBySlug(String slug) {
        Article a = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found with slug: " + slug));
        return convertToDto(a);
    }

    public Article getArticleEntityBySlug(String slug) {
        return articleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found with slug: " + slug));
    }
//    public List<ArticleCategory> getArticleCategories(){
//
//    }
    @Transactional
    public ArticleResponse createArticle(ArticlePost articleDto,String coverImageUrl) {
        Article a = new Article();
        User user = userRepository.findById(articleDto.userId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + articleDto.userId()));

        a.setBodyMarkdown(articleDto.body());
        a.setCategory(articleDto.category());
        a.setBodyHtml(markdownService.convertToHtml(articleDto.body()));
        a.setTitle(articleDto.title());
        a.setCoverImgUrl(coverImageUrl);
        a.setUser(user);

        Article saved = articleRepository.save(a);
        return convertToDto(saved);
    }

    @Transactional
    public ArticleResponse updateArticle(String slug, ArticlePost articlePost,String coverImgUrl) {
        Article a = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found with slug: " + slug));

        a.setTitle(articlePost.title());
        a.setBodyMarkdown(articlePost.body());
        a.setBodyHtml(markdownService.convertToHtml(articlePost.body()));
        a.setCoverImgUrl(coverImgUrl);

        Article updated = articleRepository.save(a);
        return convertToDto(updated);
    }

    @Transactional
    public void deleteArticle(String slug) {
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found with slug: " + slug));
        articleRepository.delete(article);
    }

    @Transactional
    public void deleteArticleById(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found with id: " + articleId));
        articleRepository.delete(article);
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<ArticleResponse> getAllByUserId(Long userId) {
        return articleRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<ArticleResponse> getAllByDate(Integer limit) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        if (limit == null) {
            return articleRepository.findAll(sort)
                    .stream()
                    .map(this::convertToDto)
                    .toList();
        }

        return articleRepository.findAll(sort)
                .stream()
                .map(this::convertToDto)
                .limit(limit)
                .toList();
    }

    public List<ArticleResponse> searchArticles(String query) {
        return articleRepository.findByTitleContainingIgnoreCase(query)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public ArticleResponse likeArticle(String slug) {
        Article article = getArticleEntityBySlug(slug);
        article.setLikes(article.getLikes() + 1);
        Article saved = articleRepository.save(article);
        return convertToDto(saved);
    }

    @Transactional
    public ArticleResponse unlikeArticle(String slug) {
        Article article = getArticleEntityBySlug(slug);
        if (article.getLikes() > 0) {
            article.setLikes(article.getLikes() - 1);
        }
        Article saved = articleRepository.save(article);
        return convertToDto(saved);
    }

    @Transactional
    public ArticleResponse dislikeArticle(String slug) {
        Article article = getArticleEntityBySlug(slug);
        article.setDislikes(article.getDislikes() + 1);
        Article saved = articleRepository.save(article);
        return convertToDto(saved);
    }

    @Transactional
    public ArticleResponse undislikeArticle(String slug) {
        Article article = getArticleEntityBySlug(slug);
        if (article.getDislikes() > 0) {
            article.setDislikes(article.getDislikes() - 1);
        }
        Article saved = articleRepository.save(article);
        return convertToDto(saved);
    }

    @Transactional
    public CommentResponse commentArticle(String slug, CommentPost commentDto) {
        Comment comment = new Comment();

        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found with slug: " + slug));
        comment.setArticle(article);

        User user = userRepository.findByUsername(commentDto.username())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + commentDto.username()));
        comment.setUser(user);

        comment.setContent(commentDto.commentText());
        Comment saved = commentRepository.save(comment);

        return new CommentResponse(
                saved.getUser().getUsername(),
                saved.getArticle().getSlug(),
                saved.getContent(),
                saved.getCommentedAt(),
                saved.getUpdatedAt()
        );
    }
    public List<ArticleResponse> getArticlesByCategory(ArticleCategory category) {
        return articleRepository.findByCategory(category)
                .stream()
                .map(this::convertToDto)
                .toList();
    }
    public List<CommentResponse> getArticleComments(String slug) {
        List<Comment> comments = commentRepository.findByArticleSlug(slug);

        return comments.stream()
                .map(c -> new CommentResponse(
                        c.getUser().getUsername(),
                        c.getArticle().getSlug(),
                        c.getContent(),
                        c.getCommentedAt(),
                        c.getUpdatedAt()
                ))
                .toList();
    }
}