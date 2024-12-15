package com.mockito.userServiceTest;

import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;
import com.mockito.repository.JunitUserRepository;
import com.mockito.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    //    @Autowired
//    private UserService userService;
//    @MockBean
//    private UserRepository userRepository;
//    @Autowired
//    private UserController userController;

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private JunitUserRepository userRepository;

    private static List<JunitUser> userList;
    private static JunitUser user;
    private static UserDto userDto;

    @BeforeAll
    static void setUpBeforeAll() {
        System.out.println("Before all tests will be pass ...");

        userList = new ArrayList<>();
        userList.add(new JunitUser(1L, "Abhay", "Singh", "abhaysingh123@gmail.com", "abhaysingh"));
        userList.add(new JunitUser(2L, "Ajay", "Kumar", "ajaykumar@gmail.com", "ajaykumar"));
        user = new JunitUser(1L, "Abhay", "Singh", "abhaysingh123@gmail.com", "abhaysingh");
        userDto = new UserDto(1L, "Abhay", "Singh", "abhaysingh123@gmail.com", "abhaysingh");
    }
    @Test
    @DisplayName("Create_User_Service_Test")
    public void createUser() {
        when(userRepository.save(user)).thenReturn(user);
        UserDto createdUser = userService.createUser(userDto);
        assertEquals(userDto,createdUser);
        verify(userRepository, times(1)).save(any(JunitUser.class));
    }
    @Test
    @DisplayName("CreateUserService_Test_Exception")
    public void createUserService_Exception() {
        when(userRepository.save(any(JunitUser.class))).thenThrow(new RuntimeException("Email cannot be null"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> userService.createUser(userDto)
        );

        assertEquals("Failed to store user data in the databaseEmail cannot be null", exception.getMessage());
        verify(userRepository, times(1)).save(any(JunitUser.class));

    }
    @Test
    @DisplayName("Get_All_User_Abhay")
    public void getAllUser() {
        when(userRepository.findAll()).thenReturn(userList);
        List<UserDto> result = userService.getAllUser();


        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    @DisplayName("GetAll_Users_Service_Exception")
    void getAllUsersService_Exception() {

        when(userRepository.findAll()).thenThrow(new RuntimeException("Users not found"));

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.getAllUser()
        );

        assertEquals("Users not found", exception.getMessage());
        verify(userRepository, times(1)).findAll();
    }
    @Test
    @DisplayName("GetUserById_Service_Test")
    // @Disabled("Currently I will skip the Test class")
    public void getUserByIdService_Test() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        UserDto response = userService.getUserById(id);
        assertEquals(1L, response.getId());
        assertNotNull(response);

    }
    @Test
    @DisplayName("GetUserById_Service_UserNotFound_Test")
    void getUserByIdService_UserNotFound() {

        Long userId = 1L;
        when(userRepository.findById(userId))
                .thenThrow(new RuntimeException("User not found with ID: " + userId));

        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> userService.getUserById(userId)
        );

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }
    @Test
    @DisplayName("DeleteByIdService_Test")
    public void deleteById() {
        Long userId = 1L;
        userService.deleteById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }
    @Test
    @DisplayName("DeleteUserById_Service_UserNotFound")
    void deleteUserByIdService_UserNotFound() {

        Long userId = 1L;
        doThrow(new RuntimeException("An error occurred while fetching the user: User not found")).when(userRepository).deleteById(userId);

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> userService.deleteById(userId)
        );

        assertEquals("An error occurred while fetching the user: User not found", exception.getMessage());
        verify(userRepository, times(1)).deleteById(userId);
    }
}
