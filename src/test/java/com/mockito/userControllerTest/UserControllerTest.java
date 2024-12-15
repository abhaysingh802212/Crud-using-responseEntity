package com.mockito.userControllerTest;

import com.mockito.controller.UserController;
import com.mockito.dto.UserDto;
import com.mockito.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    UserDto savedUserDto;
    UserDto inputUserDto;
    List<UserDto> list;

    @BeforeEach
    void setUP(){
        inputUserDto=new UserDto("Abhay","Singh","abhaysingh@gmail.com");
        savedUserDto= new UserDto(1L,"Abhay","Singh","abhaysingh@gmail.com","abhaysingh");
        list = new ArrayList<>();
        list.add(new UserDto(1L,"Abhay","Singh","abhaysingh@gmail.com","abhaysingh"));
        list.add(new UserDto(1L,"Abhay","Singh","abhaysingh@gmail.com","abhaysingh"));
    }

    @Test
    @DisplayName("Create_User_Controller_Test")
    void CreateUserController_Test() {

    when(userService.createUser(inputUserDto)).thenReturn(savedUserDto);

    ResponseEntity<UserDto> response = userController.createUser(inputUserDto);

    assertEquals(200, response.getStatusCodeValue());
    assertEquals(savedUserDto, response.getBody());
    verify(userService, times(1)).createUser(inputUserDto);
}
    @Test
    @DisplayName("CreateUser_Controller_Test")
    void CreateUserController_Exception() {

        when(userService.createUser(inputUserDto))
                .thenThrow(new RuntimeException("Failed to store user data in the database"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userController.createUser(inputUserDto)
        );

        assertEquals("Failed to store user data in the database", exception.getMessage());
        verify(userService, times(1)).createUser(inputUserDto);
    }
    @Test
    @DisplayName("GetAll_Users_Controller_test")
    void getAllUsersController_test() {

        when(userService.getAllUser()).thenReturn(list);

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(list, response.getBody());
        verify(userService, times(1)).getAllUser();
    }
    @Test
    @DisplayName("GetAll_Users_Controller_Exception")
    void getAllUsersController_Exception() {

        when(userService.getAllUser()).thenThrow(new RuntimeException("Users not found"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userController.getAllUsers()
        );

        assertEquals("Users not found", exception.getMessage());
        verify(userService, times(1)).getAllUser();
    }

    @Test
    @DisplayName("GetUserById_Controller_Test")
    void getUserByIdController_Test() {
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(inputUserDto);
        ResponseEntity<UserDto> response = userController.getUserById(userId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(inputUserDto, response.getBody());
        verify(userService, times(1)).getUserById(userId);
    }
    @Test
    @DisplayName("GetUserById_Controller_UserNotFound_Test")
    void getUserById_UserNotFound() {

        Long userId = 1L;
        when(userService.getUserById(userId))
                .thenThrow(new RuntimeException("User not found with ID: " + userId));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userController.getUserById(userId)
        );

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(userService, times(1)).getUserById(userId);
    }
    @Test
    @DisplayName("DeleteUserByIdController_Test")
    void deleteUserByIdController_Test() {
        Long userId = 1L;
        doNothing().when(userService).deleteById(userId);
        ResponseEntity<String> response = userController.deleteUserById(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User id is successfully Delete", response.getBody());
        verify(userService, times(1)).deleteById(userId);
    }
    @Test
    @DisplayName("DeleteUserById_Controller_UserNotFound")
    void deleteUserByIdController_UserNotFound() {

        Long userId = 1L;
        doThrow(new RuntimeException("An error occurred while fetching the user: User not found")).when(userService).deleteById(userId);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userController.deleteUserById(userId)
        );

        assertEquals("An error occurred while fetching the user: User not found", exception.getMessage());
        verify(userService, times(1)).deleteById(userId);
    }
}
