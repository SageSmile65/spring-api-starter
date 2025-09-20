package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Mapper.UserMapper;
import com.codewithmosh.store.dtos.ChangePasswordRequest;
import com.codewithmosh.store.dtos.RegisterUserRequest;
import com.codewithmosh.store.dtos.UpdateUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers(
            @RequestParam(required = false, defaultValue = "", name = "sort") String sort,
            @RequestHeader(required = false, name = "x-auth-token") String authToken
    ) {
        //checking if sort is valid or not
        if (!Set.of("name", "email").contains(sort)) {
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
        if (userDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //Handling post requests i.e. receiving data from frontend
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody RegisterUserRequest request,
                                              UriComponentsBuilder uriBuilder
    ) {
        var user = userMapper.toEntity(request);
        userRepository.save(user);
        var userDto = userMapper.userToUserDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
     }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") long id, @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.updateUser(request, user);
        userRepository.save(user);

        //Returning userDto respEntity to display the JSON body
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable(name = "id") long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.ok(userMapper.userToUserDto(user));
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestBody ChangePasswordRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        //If the oldPassword does not match unauthorize update
        if(!user.getPassword().equals(request.getOldPassword())) {
            return new  ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}