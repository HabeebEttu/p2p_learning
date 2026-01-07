package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.User;

import java.time.LocalDateTime;

public record AuthResponse(
        String token,
        LocalDateTime expiryDate,
        User user
) {

}
