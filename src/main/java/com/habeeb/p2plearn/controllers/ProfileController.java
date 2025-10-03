package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.ProfileDto;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.services.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final  ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable Long userId){
        ProfileDto dto = profileService.getProfileById(userId);;
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody ProfileDto profileDto){
        profileService.updateProfile(userId,profileDto);
        return ResponseEntity.ok("profile updated successfully");
    }


}
