package com.app.blogApp.users;

import com.app.blogApp.users.dtos.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest u){
        var user = User.builder()
                .username(u.getUsername())
                //TODO: create password encryption method
                .email(u.getEmail())
                .build();

        return userRepository.save(user);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUser(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new UserNotFoundException(username));
    }

    public User loginUser(String username, String password){
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        //TODO: add logic to match password
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException{
        public UserNotFoundException(Long userId) {
            super("User with id "+userId+" not found");
        }
        public UserNotFoundException(String username) {
            super("User "+username+" not found");
        }
    }
}
