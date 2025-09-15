package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserRequest {
    private String name;
    private String email;
    private String password;
}
