package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.AdminDashboardResponse;
import com.habeeb.p2plearn.dto.ArticlePost;
import com.habeeb.p2plearn.dto.ArticleResponse;
import com.habeeb.p2plearn.models.ImageTypes;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.services.AdminServiceImpl;
import com.habeeb.p2plearn.services.AuthServiceImpl;
import com.habeeb.p2plearn.services.FileStorageServiceImpl;
import com.habeeb.p2plearn.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping(value = "/api/admin",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {
    private final AdminServiceImpl adminService;
    private final AuthServiceImpl authService;
    private final SessionService sessionService;
    private final FileStorageServiceImpl fileStorageService;



    @GetMapping("/home")
    public ResponseEntity<?> getDashboardData(){
        User user  = authService.getCurrentUser();
        if(user.isAdmin()){

            return ResponseEntity.ok(adminService.getDashboardData());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @PostMapping(value = "/article/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createArticle(
            @RequestPart("article") ArticlePost articlePost,
            @RequestPart(value = "coverImage", required = false) MultipartFile coverImage
    ) {
        // 1. Check admin
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
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        User user  = authService.getCurrentUser();
        if(user.isAdmin()){
        adminService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete user");
    }
}
