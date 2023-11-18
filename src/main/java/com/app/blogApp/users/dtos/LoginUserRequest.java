package com.app.blogApp.users.dtos;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
}
