package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import org.mapstruct.Mapper;

//Maps the user to the DTO automatically
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
}
