package com.ms.app.service;

import com.ms.app.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ms.app.dto.UserDTO;
import com.ms.app.model.User;

import java.io.IOException;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final UsersRepository userRepository;
    private final CloudinaryService cloudinaryService;
    public UserServiceImpl(UsersRepository userRepository, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public User update(UserDTO user, MultipartFile avatarFile) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        User current_User;
        if(principal instanceof User) {
            current_User = (User) principal;
        } else {
            String email = authentication.getName();
            current_User = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found: " + email));
        }
        current_User.setEmail(user.getEmail());
        current_User.setDisplayname(user.getDisplayname());
        if (avatarFile != null && !avatarFile.isEmpty()) {
            Map uploadResult = cloudinaryService.uploadImage(avatarFile);
            current_User.setAvatarUrl((String) uploadResult.get("url"));
        }
        return userRepository.save(current_User);
    }
}
