package com.mockito.controller;


import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;
import com.mockito.repository.JunitUserRepository;
import com.mockito.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/junit_user")
public class UserController {

    @Autowired
   private UserService userService;
    @Autowired
    private JunitUserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return ResponseEntity.ok(user);
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("User id is successfully Delete");
    }
//    @GetMapping("/all")
//    public ResponseEntity<Object> getAllActiveUser() {
//        List<UserDto> userDto = userService.getAllActiveUsers();
//        return ResponseEntity.ok(userDto);
//    }
@GetMapping("/active")
public ResponseEntity<List<UserDto>> getAllActiveUsers() {
    List<UserDto> activeUsers = userService.getAllActiveUsers();
    return ResponseEntity.ok(activeUsers);
}

}
