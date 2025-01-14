package com.personal.portfolio.controller;

import com.personal.portfolio.dto.ApiResponse;
import com.personal.portfolio.dto.auth.LoginRequest;
import com.personal.portfolio.dto.auth.RegisterRequest;
import com.personal.portfolio.dto.auth.TokenResponse;
import com.personal.portfolio.entity.User;
import com.personal.portfolio.security.JwtTokenProvider;
import com.personal.portfolio.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(request.getUsername());

        User user = userService.findByUsername(request.getUsername());
        TokenResponse response = TokenResponse.builder()
                .token(token)
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<TokenResponse>> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());

        User registeredUser = userService.register(user);
        String token = tokenProvider.generateToken(registeredUser.getUsername());

        TokenResponse response = TokenResponse.builder()
                .token(token)
                .username(registeredUser.getUsername())
                .email(registeredUser.getEmail())
                .role(registeredUser.getRole().name())
                .build();

        return ResponseEntity.ok(ApiResponse.success("Registration successful", response));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@RequestParam String token) {
        userService.verifyEmail(token);
        return ResponseEntity.ok(ApiResponse.success("Email verified successfully", null));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestParam String email) {
        userService.resetPassword(email);
        return ResponseEntity.ok(ApiResponse.success("Password reset email sent", null));
    }

    @PostMapping("/confirm-reset")
    public ResponseEntity<ApiResponse<Void>> confirmResetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        userService.confirmResetPassword(token, newPassword);
        return ResponseEntity.ok(ApiResponse.success("Password reset successful", null));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(authentication.getName());
        userService.changePassword(user.getId(), oldPassword, newPassword);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully", null));
    }
} 