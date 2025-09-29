package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.UserDto;
import com.habeeb.p2plearn.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long userId,
            @RequestBody UserDto userDto
    ) {
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{userId}/update-password")
    public ResponseEntity<UserDto> updatePassword(
            @PathVariable Long userId,
            @RequestBody UserDto userDto,
            @RequestParam String newPassword
    ) {
        UserDto updatedUser = userService.updatePassword(userId,  newPassword);
        return ResponseEntity.ok(updatedUser);
    }
}
