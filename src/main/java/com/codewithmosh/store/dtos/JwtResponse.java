package com.codewithmosh.store.dtos;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
}
