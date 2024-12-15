package com.mockito.jwtcontrollerTest;

import com.mockito.controller.JwtController;
import com.mockito.domain.LoginResponse;
import com.mockito.domain.Users;
import com.mockito.dto.LoginUserDto;
import com.mockito.dto.RegisterUserDto;
import com.mockito.service.AuthenticationService;
import com.mockito.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserContollerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private JwtController jwtController;


    @Mock
    private JwtService jwtService;

    @Mock
    private LoginUserDto loginUserDto;

    @Mock
    private Users authenticatedUser;

    @Mock
    private LoginResponse loginResponse;
    RegisterUserDto input;
    Users users;

    @BeforeEach
     void setUp() {
        input = new RegisterUserDto("aryankumar@gmail.com", "aryankumar", "Aryan Kumar");
        users = new Users(1, "Abhay Singh", "abhaysingh@gmail.com", "abhaysingh");
    }
        @Test
        @DisplayName("Register_UserSignup_Test")
        void registerUserSignupTest() {

        when(authenticationService.signup(input)).thenReturn(users);

        ResponseEntity<Users> response = jwtController.register(input);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Abhay Singh", response.getBody().getFullName());
        assertEquals("abhaysingh@gmail.com", response.getBody().getEmail());
        verify(authenticationService, times(1)).signup(input);

    }
    @Test
    @DisplayName("RegisterUserSignup_ExceptionHandling_Test")
    void registerUserSignup_ExceptionHandling_Test() {

        when(authenticationService.signup(input)).thenThrow(new RuntimeException("ExceptionHandling_Test"));
       Exception exception = assertThrows(RuntimeException.class,()-> {
            jwtController.register(input);
        });
       assertEquals("ExceptionHandling_Test",exception.getMessage());
       verify(authenticationService,times(1)).signup(input);
    }
    @Test
    @DisplayName("Register_UserLogin_Test")
    void registerUserLoginTest() {
        when(authenticationService.authenticate(loginUserDto)).thenReturn(authenticatedUser);
        when(jwtService.generateToken(authenticatedUser)).thenReturn("jwtToken");
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        ResponseEntity<LoginResponse> response = jwtController.authenticate(loginUserDto);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("jwtToken", response.getBody().getToken());
        assertEquals(3600, response.getBody().getExpiresIn());

    }
    @Test
    @DisplayName("Authenticate_Failure")
    void Authenticate_Failure() {
        when(authenticationService.authenticate(loginUserDto)).thenThrow(new RuntimeException("Authentication failed"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            jwtController.authenticate(loginUserDto);
        });
        assertEquals("Authentication failed", exception.getMessage());
    }







}
