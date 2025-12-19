package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.dto.AuthResponse;
import com.habeeb.p2plearn.dto.LoginRequest;
import com.habeeb.p2plearn.dto.RegisterRequest;
import com.habeeb.p2plearn.exceptions.UserAlreadyExistsException;
import com.habeeb.p2plearn.models.Profile;
import com.habeeb.p2plearn.models.Rank;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.ProfileRepository;
import com.habeeb.p2plearn.repositories.UserRepository;
import com.habeeb.p2plearn.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public AuthServiceImpl(AuthenticationManager authManager,
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

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();

        User userEntity = userRepository.findByUsername(user.getUsername()).get();
        return new AuthResponse(
                jwtUtil.generateToken(user),
                userEntity
        );
    }

    @Override
    public void register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UserAlreadyExistsException("Username already taken");
        } else if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistsException("This email has already been used");
        }

        User user = new User();
        Profile profile = new Profile();

        user.setUsername(request.username());
        user.setHashPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setProfile(profile);

        profile.setUser(user);
        profile.setRank(Rank.NOVICE);
        profile.setFirstName(request.firstname());
        profile.setLastName(request.lastname());
        userRepository.save(user);
    }
}
