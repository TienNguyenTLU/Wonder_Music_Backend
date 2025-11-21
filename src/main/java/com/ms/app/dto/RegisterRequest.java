package com.ms.app.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String displayname;
    private String password;
}
