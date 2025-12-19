package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.User;

public record AuthResponse(
        String token,
        User user
) {

}
