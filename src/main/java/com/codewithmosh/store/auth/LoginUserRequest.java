package com.codewithmosh.store.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserRequest {

    @NotBlank
    @Email
    public String email;

    @NotBlank(message = "password cannot be null")
    public  String password;
}
