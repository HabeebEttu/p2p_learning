package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.ProfileDto;
import com.habeeb.p2plearn.models.ImageTypes;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.services.FileStorageService;
import com.habeeb.p2plearn.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final FileStorageService fileStorageService;

    public ProfileController(ProfileService profileService, FileStorageService fileStorageService) {
        this.profileService = profileService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long userId) {
        ProfileDto dto = profileService.getProfileById(userId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable Long userId,
            @RequestPart(value = "profile") ProfileDto profileDto,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws Exception {

        String avatarUrl = null;

        // Check if avatar is provided AND not empty
        if (avatar != null && !avatar.isEmpty()) {
            String contentType = avatar.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Only image files are allowed");
            }

            if (avatar.getSize() > 5 * 1024 * 1024) {
                throw new RuntimeException("Files must be less than 5MB");
            }

            Path storedPath = fileStorageService.store(avatar,ImageTypes.PROFILE);
            avatarUrl = fileStorageService.toPublicUrl(storedPath, ImageTypes.PROFILE);
        }


        Profile p = profileService.updateProfile(userId, profileDto, avatarUrl);

        return ResponseEntity.ok(p);
    }

    @PostMapping("/{userId}/avatar")
    public ResponseEntity<String> uploadAvatar(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) throws Exception {

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("Files must be less than 5MB");
        }

        Path storedPath = fileStorageService.store(file ,ImageTypes.PROFILE);
        String avatarUrl = fileStorageService.toPublicUrl(storedPath,ImageTypes.PROFILE );


        profileService.updateAvatar(userId, avatarUrl);

        return ResponseEntity.ok(avatarUrl);
    }
}