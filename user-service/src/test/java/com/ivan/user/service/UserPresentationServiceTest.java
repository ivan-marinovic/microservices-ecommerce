package com.ivan.user.service;

import com.ivan.user.dto.LoginDto;
import com.ivan.user.dto.RegisterDto;
import com.ivan.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class UserPresentationServiceTest {

    private UserPresentationService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserPresentationService();
    }

    @Test
    void convertToModel_shouldReturnUser() {
        // given
        RegisterDto registerDto = RegisterDto.builder()
                .fullName("test")
                .email("test")
                .password("password")
                .role("ROLE_USER")
                .build();

        // when
        User result = underTest.convertToModel(registerDto);

        // then
        assertThat(result.getFullName()).isEqualTo(registerDto.getFullName());
        assertThat(result.getEmail()).isEqualTo(registerDto.getEmail());
        assertThat(result.getPassword()).isEqualTo(registerDto.getPassword());
        assertThat(result.getRole()).isEqualTo(registerDto.getRole());
    }

    @Test
    void convertToDto_shouldReturnDto() {
        // given
        User user = User.builder()
                .userId(1)
                .fullName("test")
                .email("test")
                .build();

        // when
        RegisterDto result = underTest.convertToDto(user);

        // then
        assertThat(result.getUserId()).isEqualTo(user.getUserId());
        assertThat(result.getFullName()).isEqualTo(user.getFullName());
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void convertToDtoList_shouldReturnListOfUsers() {
        // given
        List<User> users = List.of(
                User.builder().userId(1).fullName("test1").email("test1").build(),
                User.builder().userId(2).fullName("test2").email("test2").build()
                );
        // when
        List<RegisterDto> result = underTest.convertToDtoList(users);
        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUserId()).isEqualTo(users.get(0).getUserId());
        assertThat(result.get(0).getFullName()).isEqualTo(users.get(0).getFullName());
        assertThat(result.get(0).getEmail()).isEqualTo(users.get(0).getEmail());
        assertThat(result.get(1).getUserId()).isEqualTo(users.get(1).getUserId());
        assertThat(result.get(1).getFullName()).isEqualTo(users.get(1).getFullName());
        assertThat(result.get(1).getEmail()).isEqualTo(users.get(1).getEmail());
    }

    @Test
    void convertLoginDtoToModel() {
        // given
        String email = "test@test.com";
        String password = "test";
       LoginDto loginDto = new LoginDto(email, password);

        // when
        User result = underTest.convertLoginDtoToModel(loginDto);

        // then
        assertThat(result.getEmail()).isEqualTo(loginDto.getEmail());
        assertThat(result.getPassword()).isEqualTo(loginDto.getPassword());
    }

}