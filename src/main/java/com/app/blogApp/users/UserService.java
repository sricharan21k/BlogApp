package com.app.blogApp.users;

import com.app.blogApp.users.dtos.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(CreateUserRequest u) {
        /* var user = User.builder()
                .username(u.getUsername())
                //TODO: create password encryption method
                .email(u.getEmail())
                .build(); */
        boolean userExists = userRepository.findByUsername(u.getUsername()).isPresent();
        if (userExists) throw new InvalidUserException(u.getUsername());

        User newUser = modelMapper.map(u, User.class);
        newUser.setPassword(passwordEncoder.encode(u.getPassword()));

        return userRepository.save(newUser);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public User loginUser(String username, String password) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        if(!passwordEncoder.matches(password, user.getPassword())) throw new InvalidUserCredentials();

        //TODO: add logic to match password
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(Long userId) {
            super("User with id " + userId + " not found");
        }

        public UserNotFoundException(String username) {
            super("User " + username + " not found");
        }
    }

    public static class InvalidUserException extends IllegalArgumentException {
        public InvalidUserException(String username) {
            super("User " + username + " already exists");
        }
    }

    public static class InvalidUserCredentials extends IllegalArgumentException{
        public InvalidUserCredentials() {
            super("User details doesn't match");
        }
    }

}
