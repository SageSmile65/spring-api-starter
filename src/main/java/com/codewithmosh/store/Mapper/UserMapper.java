package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //takes User entity object and converts it to userDto
    UserDto userToUserDto(User user);

    // takes userRequest object and converts it to User entity object
    User toEntity(RegisterUserRequest request);
}