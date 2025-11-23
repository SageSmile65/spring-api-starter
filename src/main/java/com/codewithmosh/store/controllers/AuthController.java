package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.LoginUserRequest;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequest request) {

        var requestPassword = request.getPassword();
        var requestEmail = request.getEmail();

        var user = userRepository.findByEmail(requestEmail).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (!passwordEncoder.matches(requestPassword, user.getPassword())) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "invalid password")); }
        return ResponseEntity.ok().build();
    }
}
