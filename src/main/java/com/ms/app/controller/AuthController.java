package com.ms.app.controller;

import com.ms.app.dto.AuthResponse;
import com.ms.app.dto.LoginRequest;
import com.ms.app.dto.RegisterRequest;
import com.ms.app.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest req) {
        return service.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest req) {
        return service.login(req);
    }
}
