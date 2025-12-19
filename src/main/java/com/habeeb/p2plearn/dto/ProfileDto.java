package com.habeeb.p2plearn.dto;

import com.habeeb.p2plearn.models.Follow;
import com.habeeb.p2plearn.models.FriendRequest;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.models.Rank;

import java.util.List;
import java.util.Set;

public record ProfileDto(
        String bio,
        String avatarUrl,
        Rank rank,
        String firstName,
        String lastName,
        int xp,
        List<Follow> followers,
        List<Follow> following,
        Set<Profile> friends,
        List<FriendRequest> friendRequests
) {
}
