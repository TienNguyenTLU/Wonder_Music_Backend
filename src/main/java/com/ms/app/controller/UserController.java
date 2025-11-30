package com.ms.app.controller;

import com.ms.app.dto.UserDTO;
import com.ms.app.model.User;
import com.ms.app.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @RequestPart("user") UserDTO userDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        User updatedUser = userService.update(userDTO, file);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(401).build();
        }
        User currentUser = (User) authentication.getPrincipal();
        UserDTO response = new UserDTO();
        response.setDisplayname(currentUser.getDisplayname());
        response.setEmail(currentUser.getEmail());
        response.setAvatarUrl(currentUser.getAvatarUrl());
        return ResponseEntity.ok(response);
    }
}
