package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Mapper.UserMapper;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @GetMapping
    public Iterable<UserDto> getAllUsers(@RequestParam(required = false,defaultValue = "",name = "sort") String sort) { //required is of great help
        //checking if sort is valid or not
        if(!Set.of("name", "email").contains(sort)){
            sort = "name";
        }
        var userList = userRepository.findAll(Sort.by(sort)).stream()
                .map(user -> userMapper.userToUserDto(user))
                .toList();
        return userList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        var user = userRepository.findById(id).orElse(null);
        var userDto = userMapper.userToUserDto(user);
        if(userDto == null){
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
