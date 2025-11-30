package com.ms.app.service;

import com.ms.app.dto.AuthResponse;
import com.ms.app.dto.LoginRequest;
import com.ms.app.dto.RegisterRequest;
import com.ms.app.model.User;
import com.ms.app.repository.UsersRepository;
import com.ms.app.Security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UsersRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UsersRepository userRepo, PasswordEncoder encoder, JwtService jwtService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }
    public AuthResponse register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername()))
            throw new RuntimeException("Username already exists");
        if (userRepo.existsByEmail(req.getEmail()))
            throw new RuntimeException("Email already exists");
        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setDisplayname(req.getDisplayname());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole("USER");
        u.setCreatedAt(LocalDateTime.now());
        userRepo.save(u);
        return new AuthResponse(jwtService.generateToken(u.getUsername()));
    }

    public AuthResponse login(LoginRequest req) {
        User u = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!encoder.matches(req.getPassword(), u.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return new AuthResponse(jwtService.generateToken(u.getUsername()));
    }
}
