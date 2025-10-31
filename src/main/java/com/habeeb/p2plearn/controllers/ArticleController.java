package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.ArticlePost;
import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.dto.CommentPost;
import com.habeeb.p2plearn.dto.CommentResponse;
import com.habeeb.p2plearn.services.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody ArticlePost articleDto) {
        articleService.createArticle(articleDto);
        return ResponseEntity.ok("Article created successfully");
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable String slug) {
        return ResponseEntity.ok(articleService.getArticleBySlug(slug));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<String> updateArticle(@PathVariable String slug, @RequestBody ArticlePost articlePost) {
        articleService.updateArticle(slug, articlePost);
        return ResponseEntity.ok("Article updated successfully");
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<String> deleteArticle(@PathVariable String slug) {
        articleService.deleteArticle(slug);
        return ResponseEntity.ok("Article deleted");
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ArticleResponse>> getAllArticlesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(articleService.getAllByUserId(userId));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ArticleResponse>> getAllArticlesByDate(@RequestParam Integer limit) {
        return ResponseEntity.ok(articleService.getAllByDate(limit));
    }



    @GetMapping("/search")
    public ResponseEntity<List<ArticleResponse>> searchArticles(@RequestParam String query) {
        return ResponseEntity.ok(articleService.searchArticles(query));
    }


    @PostMapping("/{slug}/comment")
    public ResponseEntity<String> postComment(@PathVariable String slug, @RequestBody CommentPost comment){
        articleService.commentArticle(slug,comment);
        return ResponseEntity.ok("Comment submitted successfully");
    }
    @GetMapping("/comment/{slug}")
    public ResponseEntity<List<CommentResponse>> getCommentsForArticle(@PathVariable String slug){
        return ResponseEntity.ok(articleService.getArticleComments(slug));

    }



}
