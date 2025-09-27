package com.habeeb.p2plearn.dto;

public record AuthResponse(
        String token,
        String username,
        String email
) {

}
