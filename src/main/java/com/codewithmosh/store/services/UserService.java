package com.codewithmosh.store.services;

import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return new User(
                user.getName(),
                user.getPassword(),
                Collections.emptyList()
        );
    }
}
