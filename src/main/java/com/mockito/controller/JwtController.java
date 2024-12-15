package com.mockito.controller;

import com.mockito.domain.LoginResponse;
import com.mockito.domain.Users;
import com.mockito.dto.LoginUserDto;
import com.mockito.dto.RegisterUserDto;
import com.mockito.service.AuthenticationService;
import com.mockito.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@Slf4j
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    public void AuthenticationController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Users> register(@RequestBody RegisterUserDto registerUserDto) {
        Users registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
       // try {
            log.info("Entering into authenticationService-> authenticate method");
            Users authenticatedUser = authenticationService.authenticate(loginUserDto);

            log.info("Entering into jwtService");
            String jwtToken = jwtService.generateToken(authenticatedUser);

            log.info("Creating login response");
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtToken);
            loginResponse.setExpiresIn(jwtService.getExpirationTime());

            return ResponseEntity.ok(loginResponse);
//        } catch (Exception e) {
//            log.info("Exception: " + e);
//            throw new RuntimeException(e);
//        }
    }
}
