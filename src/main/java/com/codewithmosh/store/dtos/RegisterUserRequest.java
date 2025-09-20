package com.codewithmosh.store.dtos;

import com.codewithmosh.store.validation.Lowercase;
import jakarta.validation.constraints.Email;
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

    @NotBlank
    @Email(message = "Email must be valid")
    @Lowercase(message = "Must be lowecase!")
    private String email;

    @NotBlank
    @Size(min = 6,max=25,message = "Password must be 6 to 25 characters long")
    private String password;
}
