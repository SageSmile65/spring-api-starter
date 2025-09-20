package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserRequest {
    @NotBlank
    @Size(max = 255,message = "Name must be less than 255 chararcters!")
    private String name;
    private String email;
    private String password;
}
