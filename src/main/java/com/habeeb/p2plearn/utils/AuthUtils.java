package com.habeeb.p2plearn.utils;

import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    private final UserService userService ;


    public AuthUtils(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return null;
        }
        Object principal = auth.getPrincipal();
        if(principal instanceof UserDetails){
            return userService.findByUsername(((UserDetails)principal).getUsername());

        }
        return null;
    }
}
