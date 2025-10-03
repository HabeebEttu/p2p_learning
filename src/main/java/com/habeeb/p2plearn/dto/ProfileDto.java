package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.models.Rank;

import java.util.List;

public record ProfileDto(
        String bio,
        String avatarUrl,
        Rank rank,
        int xp,
        List<Profile> followers,
        List<Profile> following
) {
}
