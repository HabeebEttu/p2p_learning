package com.habeeb.p2plearn.services;

import com.habeeb.p2plearn.models.Role;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService {
private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        List<String> roles = new ArrayList<>();
        String role = Role.USER.toString();
        if(user.isAdmin()){
            role = Role.ADMIN.toString();
        }
        roles.add(role);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getHashPassword(),
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}
