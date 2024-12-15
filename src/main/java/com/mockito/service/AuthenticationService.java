package com.mockito.service;

import com.mockito.domain.Users;
import com.mockito.dto.RegisterUserDto;
import com.mockito.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;
@Service
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(
            UsersRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

public Users signup(RegisterUserDto registerUserDto) {
    String encodedPassword = passwordEncoder.encode(registerUserDto.getPassword());
    System.out.println("Encoded Password: " + encodedPassword);

    Users newUser = new Users(
            registerUserDto.getFullName(),
            registerUserDto.getEmail(),
            encodedPassword
    );

    System.out.println("Saving User: " + newUser);
    return userRepository.save(newUser);
}
    public Users authenticate(com.mockito.dto.LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found: " + input.getEmail()));
    }
}
