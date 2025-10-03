package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.ProfileDto;
import com.habeeb.p2plearn.models.Follow;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.repositories.ProfileRepository;
import com.habeeb.p2plearn.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public ProfileService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }
    public ProfileDto convertProfileToDto(Profile profile){
        List<Profile> followers = profile.getFollowers().stream().map(Follow::getFollower).toList();
        List<Profile> following = profile.getFollowing().stream().map(Follow::getFollowed).toList();
        return new ProfileDto(profile.getBio(),profile.getAvatarUrl(),profile.getRank(),profile.getXp(),followers,following);
    }


    public ProfileDto getProfileById(Long userId) {
        Profile p = profileRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Profile was not found"));

        return convertProfileToDto(p);
    }

    public void updateProfile(Long userId, ProfileDto profileDto) {

        
    }
}
