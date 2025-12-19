package com.habeeb.p2plearn.dto;

public record RegisterRequest(
        String username,
        String password,
        String email,
        String firstname,
        String lastname
) {
}
