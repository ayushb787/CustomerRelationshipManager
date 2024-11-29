package org.demo.crm.auth.controller;

import org.demo.crm.auth.dto.JwtResponse;
import org.demo.crm.auth.dto.LoginRequest;
import org.demo.crm.auth.dto.SignupRequest;
import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<String>> hello() {
        return ResponseEntity.ok(ApiResponse.success("Hello Ayush", "Request successful"));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            authService.signup(signupRequest);
            return ResponseEntity.ok(ApiResponse.success("User registered successfully", "User created"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("User registration failed: " + e.getMessage(), 400));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.login(loginRequest);
            JwtResponse jwtResponse = new JwtResponse(token, loginRequest.getUsername(), "ROLE_USER");
            return ResponseEntity.ok(ApiResponse.success(jwtResponse, "Login successful"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid username or password", 401));
        }
    }
}
