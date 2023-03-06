package com.ivan.user.service;

import com.ivan.user.config.security.JwtService;
import com.ivan.user.exception.UserAlreadyExistsException;
import com.ivan.user.exception.UserDoesNotExistsException;
import com.ivan.user.model.User;
import com.ivan.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void registerUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistsException("user with email " + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    public void updateUser(User user, Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new UserDoesNotExistsException("user with id " + userId + " does not exists");
        }
        User updatedUser = optionalUser.get();
        updatedUser.setFullName(user.getFullName());
        updatedUser.setEmail(user.getEmail());

        userRepository.save(updatedUser);
    }

    public void deleteUser(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new UserDoesNotExistsException("user with " + userId + " does not exists");
        }
        userRepository.deleteById(userId);
    }

    public User getUserByToken(String token) {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        Optional<User> optionalUser = userRepository.findByEmail(username);
        User user = optionalUser.get();
        return user;
    }

}
