package com.ivan.user.controller;

import com.ivan.user.dto.RegisterDto;
import com.ivan.user.model.User;
import com.ivan.user.response.ApiResponse;
import com.ivan.user.service.UserService;
import com.ivan.user.service.presentation.UserPresentationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserPresentationService userPresentationService;

    public UserController(UserService userService, UserPresentationService userPresentationService) {
        this.userService = userService;
        this.userPresentationService = userPresentationService;
    }

    @GetMapping
    public ResponseEntity<List<RegisterDto>> getAllUsers() {
        List<User> users= userService.getAllUsers();
        List<RegisterDto> usersDto = userPresentationService.convertToDtoList(users);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") Integer userId, @Valid @RequestBody RegisterDto registerDto) {
        User user = userPresentationService.convertToModel(registerDto);
        userService.updateUser(user, userId);
        return new ResponseEntity<>(new ApiResponse(1, "user updated successfully"), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse(1, "user deleted successfully"), HttpStatus.OK);
    }
}
