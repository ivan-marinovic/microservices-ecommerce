package com.ivan.user.service;

import com.ivan.user.dto.LoginDto;
import com.ivan.user.dto.RegisterDto;
import com.ivan.user.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPresentationService {

    public User convertToModel(RegisterDto registerDto) {
        return User.builder()
                .fullName(registerDto.getFullName())
                .email(registerDto.getEmail())
                .password(registerDto.getPassword())
                .role(registerDto.getRole())
                .build();
    }

    public RegisterDto convertToDto(User user) {
        return RegisterDto.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .build();
    }

    public List<RegisterDto> convertToDtoList(List<User> users) {
        List<RegisterDto> usersDto = new ArrayList<>();
        users.forEach((user) -> usersDto.add(convertToDto(user)));
        return usersDto;
    }

    public User convertLoginDtoToModel(LoginDto loginDto) {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPassword(loginDto.getPassword());
        return user;
    }
}
