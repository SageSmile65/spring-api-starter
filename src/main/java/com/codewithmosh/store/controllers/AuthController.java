package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Mapper.UserMapper;
import com.codewithmosh.store.dtos.JwtResponse;
import com.codewithmosh.store.dtos.LoginUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.JwtService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserMapper userMapper;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginUserRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token =  jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void>  handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/validate")
    public boolean validateToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = (String)authentication.getPrincipal();

        var user =  userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }
}