package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.ProfileDto;
import com.habeeb.p2plearn.models.Follow;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.repositories.ProfileRepository;
import com.habeeb.p2plearn.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }
    public ProfileDto convertProfileToDto(Profile profile){
        return new ProfileDto(profile.getBio(),profile.getAvatarUrl(),profile.getRank(),profile.getXp(),profile.getFollowers(),profile.getFollowing(),profile.getFriends(),profile.getFriendRequests());
    }


    public ProfileDto getProfileById(Long userId) {
        Profile p = profileRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Profile was not found"));

        return convertProfileToDto(p);
    }

    public void updateProfile(Long userId, ProfileDto profileDto) {
        Profile p = profileRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("Enter a proper user Id"));
        p.setRank(profileDto.rank());
        p.setXp(profileDto.xp());
        p.setBio(profileDto.bio());
        p.setAvatarUrl(profileDto.avatarUrl());
        p.setFollowers(profileDto.followers());
        p.setFollowing(profileDto.following());
        p.setFriendRequests(profileDto.friendRequests());
        p.setFriends(profileDto.friends());
    }

}
