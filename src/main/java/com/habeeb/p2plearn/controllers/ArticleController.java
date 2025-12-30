package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.ArticlePost;
import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.dto.CommentPost;
import com.habeeb.p2plearn.dto.CommentResponse;
import com.habeeb.p2plearn.models.ArticleCategory;
import com.habeeb.p2plearn.models.ImageTypes;
import com.habeeb.p2plearn.services.ArticleService;
import com.habeeb.p2plearn.services.FileStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final FileStorageServiceImpl fileStorageService;

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@RequestPart(value = "article") ArticlePost articlePost, @RequestPart(value="cover image",required = true) MultipartFile coverImage) throws IOException {
        String coverImageUrl = null;
        if(!coverImage.isEmpty()){
            String contentType = coverImage.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                throw new RuntimeException("Only image files are allowed");
            }if(coverImage.getSize() > 1024*1024*5){
                throw new RuntimeException("Files must be less than 5MB");
            }
            Path storedPath = fileStorageService.store(coverImage, ImageTypes.ARTICLE_COVER);
            coverImageUrl = storedPath.toString();
        }
        ArticleResponse response = articleService.createArticle(articlePost,coverImageUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable String slug) {
        ArticleResponse response = articleService.getArticleBySlug(slug);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticles(
            @RequestParam(required = false) Integer limit) {
        List<ArticleResponse> articles = limit != null
                ? articleService.getAllByDate(limit)
                : articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ArticleResponse>> getArticlesByUser(@PathVariable Long userId) {
        List<ArticleResponse> articles = articleService.getAllByUserId(userId);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleResponse>> searchArticles(@RequestParam String query) {
        List<ArticleResponse> articles = articleService.searchArticles(query);
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable String slug,
            @RequestPart(value = "article"
            ) ArticlePost articlePost,@RequestPart(value = "coverImg" ,required = false) MultipartFile coverImage) throws IOException {
        String coverImgUrl = null;
        if(!coverImage.isEmpty() || coverImage !=null){

            String contentType = coverImage.getContentType();
            if(contentType ==null || contentType.startsWith("image/")){
                throw new RuntimeException("Only image files are allowed");
            }
            if(coverImage.getSize() > 1024*1024*5){
                throw new RuntimeException("Image should always be less than 5MB");
            }
            Path coverImgPath= fileStorageService.store(coverImage,ImageTypes.ARTICLE_COVER);
            coverImgUrl = coverImgPath.toString();
        }

        ArticleResponse response = articleService.updateArticle(slug, articlePost,coverImgUrl);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteArticle(@PathVariable String slug) {
        articleService.deleteArticle(slug);
        return ResponseEntity.noContent().build();
    }

    // Like/Dislike endpoints
    @PostMapping("/{slug}/like")
    public ResponseEntity<ArticleResponse> likeArticle(@PathVariable String slug) {
        ArticleResponse response = articleService.likeArticle(slug);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ArticleResponse>> getArticlesByCategory(
            @PathVariable ArticleCategory category) {
        List<ArticleResponse> articles = articleService.getArticlesByCategory(category);
        return ResponseEntity.ok(articles);
    }
    @GetMapping("/categories")
    public ResponseEntity<List<ArticleCategory>> getArticleCategories(){
        ArticleCategory[] categoriesArray = ArticleCategory.values();
        return ResponseEntity.ok(Arrays.asList(categoriesArray));
    }

    @DeleteMapping("/{slug}/like")
    public ResponseEntity<ArticleResponse> unlikeArticle(@PathVariable String slug) {
        ArticleResponse response = articleService.unlikeArticle(slug);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{slug}/dislike")
    public ResponseEntity<ArticleResponse> dislikeArticle(@PathVariable String slug) {
        ArticleResponse response = articleService.dislikeArticle(slug);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{slug}/dislike")
    public ResponseEntity<ArticleResponse> undislikeArticle(@PathVariable String slug) {
        ArticleResponse response = articleService.undislikeArticle(slug);
        return ResponseEntity.ok(response);
    }

    // Comment endpoints
    @PostMapping("/{slug}/comments")
    public ResponseEntity<CommentResponse> commentArticle(
            @PathVariable String slug,
            @RequestBody CommentPost commentPost) {
        CommentResponse response = articleService.commentArticle(slug, commentPost);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{slug}/comments")
    public ResponseEntity<List<CommentResponse>> getArticleComments(@PathVariable String slug) {
        List<CommentResponse> comments = articleService.getArticleComments(slug);
        return ResponseEntity.ok(comments);
    }
}