package com.habeeb.p2plearn.dto;

public record ProfileDto(
        Long id,
        String bio,
        String avatarUrl,
        String rank,
        int xp,
        int followersCount,
        int followingCount
) {
}
