package com.app.blogApp.users.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NonNull;

@Data
public class CreateUserResponse {
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String image;
}
