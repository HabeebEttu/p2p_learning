package com.habeeb.p2plearn.controllers;

import com.habeeb.p2plearn.dto.AdminDashboardResponse;
import com.habeeb.p2plearn.models.User;
import com.habeeb.p2plearn.services.AdminServiceImpl;
import com.habeeb.p2plearn.services.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminServiceImpl adminService;
    private final AuthServiceImpl authService;

    public AdminController(AdminServiceImpl adminService, AuthServiceImpl authService) {
        this.adminService = adminService;
        this.authService = authService;
    }

    @GetMapping("/home")

    public ResponseEntity<?> getDashboardData(){
        User user  = authService.getCurrentUser();
        if(user.isAdmin()){
            return ResponseEntity.ok();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
