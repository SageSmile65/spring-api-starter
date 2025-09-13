package com.codewithmosh.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter //So spring can get data from these fields and create JSON
public class UserDto {
    private long id;
    private String name;
    private String email;
}
