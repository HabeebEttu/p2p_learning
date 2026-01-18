package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.ArticlePost;
import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.dto.QuizPost;
import com.habeeb.p2plearn.dto.QuizResponse;
import com.habeeb.p2plearn.models.ImageTypes;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.services.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/admin",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {
    private final AdminServiceImpl adminService;
    private final AuthServiceImpl authService;
    private final FileStorageServiceImpl fileStorageService;
    private final QuizServiceImpl quizService;


    @GetMapping("/home")
    public ResponseEntity<?> getDashboardData(){
        User user  = authService.getCurrentUser();
        if(user.isAdmin()){

            return ResponseEntity.ok(adminService.getDashboardData());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping("/make/{userId}")
    public ResponseEntity<?> makeAdmin(@PathVariable Long userId){
        User user  = authService.getCurrentUser();
        if(user.isAdmin()){
            if(Objects.equals(userId, user.getId())){
                return ResponseEntity.ok("User is already an admin");
            }
            adminService.makeAdmin(userId);
            return ResponseEntity.ok("User is now an admin");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized to access this feature");
    }
    @PostMapping(value = "/article/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(
            @RequestPart("article") ArticlePost articlePost,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage
    ) {

        User user = authService.getCurrentUser();
        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }

        try {
            String coverImageUrl = null;

            // 2. Validate and upload image if present
            if (coverImage != null && !coverImage.isEmpty()) {
                // Validate file type
                String contentType = coverImage.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest()
                            .body("Only image files allowed");
                }
                if (coverImage.getSize() > 5 * 1024 * 1024) {
                    return ResponseEntity.badRequest()
                            .body("File must be less than 5MB");
                }
                coverImageUrl = fileStorageService.toPublicUrl(fileStorageService.store(coverImage, ImageTypes.ARTICLE_COVER),ImageTypes.ARTICLE_COVER);
            }
            ArticleResponse response = adminService.createArticle(articlePost, coverImageUrl);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }
    @PostMapping(value = "/article/edit/{articleId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editArticle(@PathVariable Long articleId ,
                                         @RequestPart("article") ArticlePost articlePost,
                                         @RequestPart(value = "coverImage", required = false) MultipartFile coverImage, HttpServletRequest request){
        User user = authService.getCurrentUser();
        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }
        try {
            String coverImageUrl = null;


            if (coverImage != null && !coverImage.isEmpty()) {
                String contentType = coverImage.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest()
                            .body("Only image files allowed");
                }
                if (coverImage.getSize() > 5 * 1024 * 1024) {
                    return ResponseEntity.badRequest()
                            .body("File must be less than 5MB");
                }
                coverImageUrl = fileStorageService.toPublicUrl(fileStorageService.store(coverImage, ImageTypes.ARTICLE_COVER),ImageTypes.ARTICLE_COVER);
            }
            ArticleResponse response = adminService.editArticles(articleId,articlePost,coverImageUrl);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        User user  = authService.getCurrentUser();
        if(user.isAdmin()){
        adminService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
    }
    @DeleteMapping("/article/delete/{articleId}")
    public  ResponseEntity<?> deleteArticle(@PathVariable Long articleId){
        User user = authService.getCurrentUser();
        if(user.isAdmin()){
            adminService.deleteArticles(articleId);
            return ResponseEntity.ok("Article deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user not an admin");

    }
    @PostMapping("/quizzes")
    public ResponseEntity<?> createQuiz(@RequestBody QuizPost quizPost){
        User u = authService.getCurrentUser();
        if(!u.isAdmin()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin privileges required");
        }
        try {
            QuizResponse response = quizService.createQuiz(quizPost, u.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failed to create quiz "+ e.getMessage());
        }

    }
    @GetMapping("/quizzes/all")
    public ResponseEntity<?> getAllQuizzes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        User user = authService.getCurrentUser();
        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }

        Page<QuizResponse> quizPage = quizService.getAllQuizzes(page, size);

        Map<String, Object> response = new HashMap<>();
        response.put("quizzes", quizPage.getContent());
        response.put("currentPage", quizPage.getNumber());
        response.put("totalItems", quizPage.getTotalElements());
        response.put("totalPages", quizPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
    @PostMapping("/revoke/{userId}")
    public ResponseEntity<?> removeAdmin(@PathVariable Long userId){
        User currentUser = authService.getCurrentUser();

        if(!currentUser.isAdmin()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }

        if(Objects.equals(userId, currentUser.getId())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Cannot remove your own admin privileges");
        }

        try {
            adminService.removeAdmin(userId);
            return ResponseEntity.ok("Admin privileges removed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/quiz/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }

        try {
            QuizResponse response = quizService.getQuizById(id, true);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @PutMapping("/quiz/{id}")
    public ResponseEntity<?> updateQuiz(
            @PathVariable Long id,
            @RequestBody QuizPost quizPost
    ) {
        User user = authService.getCurrentUser();
        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }

        try {
            QuizResponse response = quizService.updateQuiz(id, quizPost);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @DeleteMapping("/quizzes/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId){
        User user = authService.getCurrentUser();
        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }
        try {
            quizService.deleteQuiz(quizId);
            return ResponseEntity.ok("Quiz deleted successfully");
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/quiz/{id}/stats")
    public ResponseEntity<?> getQuizStats(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        if (!user.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Admin access required");
        }

        try {
            Map<String, Object> stats = quizService.getQuizStatistics(id);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
