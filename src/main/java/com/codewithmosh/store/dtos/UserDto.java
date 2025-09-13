package com.codewithmosh.store.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private String email;
}
