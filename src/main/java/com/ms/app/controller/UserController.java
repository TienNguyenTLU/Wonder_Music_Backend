package com.ms.app.controller;

import com.ms.app.dto.UserDTO;
import com.ms.app.model.User;
import com.ms.app.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/useredit")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping
    public ResponseEntity<User> updateUser(
            @RequestPart("user") UserDTO userDTO,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        User updatedUser = userService.update(userDTO, file);
        return ResponseEntity.ok(updatedUser);
    }
}
