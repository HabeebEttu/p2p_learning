package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.AuthResponse;
import com.habeeb.p2plearn.dto.LoginRequest;
import com.habeeb.p2plearn.dto.RegisterRequest;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.models.Rank;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.ProfileRepository;
import com.habeeb.p2plearn.repositories.UserRepository;
import com.habeeb.p2plearn.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public AuthController(AuthenticationManager authManager,
                          JwtUtil jwtUtil,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          ProfileRepository profileRepository) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileRepository = profileRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        AuthResponse response = new AuthResponse(
                jwtUtil.generateToken(user),
                user.getUsername(),
                ((User) user).getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already taken");
        } else if (userRepository.findByEmail(request.email()).isPresent()) {
            return ResponseEntity.badRequest().body("This email has already been used");
        }


        User user = new User();
        Profile profile = new Profile();

        user.setUsername(request.username());
        user.setHashPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setProfile(profile);

        profile.setUser(user);
        profile.setRank(Rank.NOVICE);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
