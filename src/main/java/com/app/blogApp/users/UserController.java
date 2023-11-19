package com.app.blogApp.users;

import com.app.blogApp.common.ErrorResponse;
import com.app.blogApp.users.dtos.CreateUserRequest;
import com.app.blogApp.users.dtos.UserResponse;
import com.app.blogApp.users.dtos.LoginUserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String getUsers() {
        return "users";
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest userRequest) {
        var user = userService.createUser(userRequest);
        URI uri = URI.create("/users/" + user.getId());
        return ResponseEntity.created(uri).body(modelMapper.map(user, UserResponse.class));
    }

    @GetMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginUserRequest login)  {
        var user = userService.loginUser(login.getUsername(), login.getPassword());
//        return ResponseEntity.ok(modelMapper.map(user, UserResponse.class));
        return ResponseEntity.ok(user);
    }

    @ExceptionHandler({UserService.UserNotFoundException.class,
            UserService.InvalidUserException.class,
            UserService.InvalidUserCredentials.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        HttpStatus status;
        String message = ex.getMessage();

        if (ex instanceof UserService.UserNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (ex instanceof UserService.InvalidUserException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof UserService.InvalidUserCredentials) {
            status = HttpStatus.UNAUTHORIZED;
        } else {
            message = "Something went wrong";
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponse response = ErrorResponse
                .builder()
                .message(message)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
