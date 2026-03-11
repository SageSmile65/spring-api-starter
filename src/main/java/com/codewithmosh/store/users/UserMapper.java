package com.codewithmosh.store.users;

import com.codewithmosh.store.auth.RegisterUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    //takes User entity object and converts it to userDto
    UserDto userToUserDto(User user);

    // takes userRequest object and converts it to User entity object
    User toEntity(RegisterUserRequest request);

    void updateUser(UpdateUserRequest request, @MappingTarget User user);
}