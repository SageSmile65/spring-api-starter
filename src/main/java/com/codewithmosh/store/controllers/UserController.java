package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public Iterable<User> getUser() {
        var userList = userRepository.findAll();
        return userList;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        var user = userRepository.findById(id).orElse(null);
        return user;
    }
}
