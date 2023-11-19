package com.app.blogApp.users.dtos;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String username;
    private String password;
}
