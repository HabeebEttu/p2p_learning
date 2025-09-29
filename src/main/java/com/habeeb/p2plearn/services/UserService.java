package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.UserDto;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private UserDto convertTODto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertTODto).toList();
    }

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        return convertTODto(user);
    }

    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        userRepository.save(user);
        return convertTODto(user);
    }

    public UserDto updatePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        user.setHashPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return convertTODto(user);
    }
}
