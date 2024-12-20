package org.demo.crm.auth.service;

import org.demo.crm.auth.dto.LoginRequest;
import org.demo.crm.auth.dto.SignupRequest;
import org.demo.crm.auth.exception.UserAlreadyExistsException;
import org.demo.crm.auth.exception.UserNotFoundException;
import org.demo.crm.auth.model.User;
import org.demo.crm.auth.repository.UserRepository;
import org.demo.crm.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;


       public String signup(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email is already registered");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPasswordHash(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setRole(signupRequest.getRole());
        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException("Invalid username or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new UserNotFoundException("Invalid username or password");
        }

        user.setLastLogin(java.time.LocalDateTime.now());
        userRepository.save(user);

        return jwtUtils.generateToken( user.getUsername(), user.getRole());
    }

    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getRole();
    }

    public Long getUserId(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getUserId();
    }

}
