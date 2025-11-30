package com.ms.app.service;

import com.ms.app.dto.UserDTO;
import com.ms.app.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User update(UserDTO user, MultipartFile avatarFile) throws IOException;
}
