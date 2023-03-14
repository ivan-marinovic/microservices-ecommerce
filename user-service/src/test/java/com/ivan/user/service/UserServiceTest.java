package com.ivan.user.service;

import com.ivan.user.config.security.JwtService;
import com.ivan.user.exception.UserAlreadyExistsException;
import com.ivan.user.exception.UserDoesNotExistsException;
import com.ivan.user.model.User;
import com.ivan.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository, passwordEncoder, jwtService);
    }

    @Test
    void canGetAllUsers() {
        // when
        underTest.getAllUsers();
        // then
        verify(userRepository).findAll();
    }

    @Test
    void canRegisterUser() {
        // given
        User user = User.builder()
                .userId(1)
                .email("test@test.com")
                .fullName("test")
                .password("test")
                .role("ROLE_ADMIN")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded-password");

        // when
        underTest.registerUser(user);
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void shouldNotRegisterUser_userAlreadyExists() {
        // given
        User user = User.builder()
                .userId(1)
                .email("test@test.com")
                .fullName("test")
                .password("test")
                .role("ROLE_ADMIN")
                .build();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> underTest.registerUser(user));

        // then
        verify(userRepository).findByEmail(user.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void canDeleteUser() {
        // given
        int userId = 1;
        User user = User.builder()
                .userId(1)
                .email("test@test.com")
                .fullName("test")
                .password("test")
                .role("ROLE_ADMIN")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        underTest.deleteUser(userId);

        // then
        verify(userRepository).findById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void canNotDeleteUser_UserDoesNotExists() {
        // given
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        Assertions.assertThrows(UserDoesNotExistsException.class, () -> underTest.deleteUser(userId));

        // then
        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }


    @Test
    void canUpdateUser() {
        // given
        User user = User.builder()
                .userId(1)
                .email("test@test.com")
                .fullName("test")
                .password("test")
                .role("ROLE_ADMIN")
                .build();
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));

        // when
        underTest.updateUser(user, user.getUserId());
        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canNotUpdateUser_UserDoesNotExists() {
        // given
        int userId = 1;
        User user = User.builder()
                .userId(2)
                .email("test@test.com")
                .fullName("test")
                .password("test")
                .role("ROLE_ADMIN")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when
        Assertions.assertThrows(UserDoesNotExistsException.class, () -> underTest.updateUser(user, userId));

        // then
        verify(userRepository).findById(userId);
        verifyNoMoreInteractions(userRepository);
    }




}
