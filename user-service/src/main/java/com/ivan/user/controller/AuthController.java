package com.ivan.user.controller;

import com.ivan.user.config.security.JwtService;
import com.ivan.user.dto.LoginDto;
import com.ivan.user.dto.RegisterDto;
import com.ivan.user.model.User;
import com.ivan.user.response.ApiResponse;
import com.ivan.user.service.UserService;
import com.ivan.user.service.presentation.UserPresentationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final UserPresentationService userPresentationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, UserPresentationService userPresentationService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.userPresentationService = userPresentationService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping
    public Boolean validateToken() {
        return true;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        User user = userPresentationService.convertToModel(registerDto);
        userService.registerUser(user);
        return new ResponseEntity<>(new ApiResponse(1, "user created successfully"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User user = userPresentationService.convertLoginDtoToModel(loginDto);
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
