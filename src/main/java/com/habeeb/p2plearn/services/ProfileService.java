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
        return new ProfileDto(profile.getBio(),profile.getAvatarUrl(),profile.getRank(),profile.getFirstName(),profile.getLastName(),profile.getXp(),profile.getFollowers(),profile.getFollowing(),profile.getFriends(),profile.getFriendRequests());
    }


    public ProfileDto getProfileById(Long userId) {
        Profile p = profileRepository.findByUserId(userId).orElseThrow(()-> new RuntimeException("Profile was not found"));

        return convertProfileToDto(p);
    }

    public Profile updateProfile(Long userId, ProfileDto profileDto, String avatarUrl) {
        Profile p = profileRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("Enter a proper user Id"));

        p.setBio(profileDto.bio());
        p.setFirstName(profileDto.firstName());
        p.setLastName(profileDto.lastName());
        if (avatarUrl != null && !avatarUrl.isEmpty()) {
            p.setAvatarUrl(avatarUrl);
        }
        profileRepository.save(p);
        return p;
    }
    public void updateAvatar(Long userId, String avatarUrl) {
        Profile p = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        p.setAvatarUrl(avatarUrl);
        profileRepository.save(p);
    }

}
