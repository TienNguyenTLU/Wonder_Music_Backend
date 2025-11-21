package com.ms.app.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class RegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 150, message = "Username must be between 4 and 150 characters")
    private String username;
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;
    @Size(min = 4, max = 150, message = "Displayname must be between 4 and 150 characters")
    @NotBlank(message = "Displayname is required")
    private String displayname;
    @NotBlank(message = "Password is required")
    private String password;
}
