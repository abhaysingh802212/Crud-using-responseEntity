package com.mockito.controller;

import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;
import com.mockito.service.JunitUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class JunitUserController {

    @Autowired
    private JunitUserService junitUserService;

    @GetMapping("/by-first-name")
    public ResponseEntity<List<JunitUser>> getUsersByFirstName(@RequestParam String firstName) {
        List<JunitUser> users = junitUserService.getUsersByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-first-and-last-name")
    public ResponseEntity<List<JunitUser>> getUsersByFirstNameAndLastName(
            @RequestParam String firstName, @RequestParam String lastName) {
        List<JunitUser> users = junitUserService.getUsersByFirstNameAndLastName(firstName, lastName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-last-name")
    public ResponseEntity<List<JunitUser>> getUsersByLastName(@RequestParam String lastName) {
        List<JunitUser> users = junitUserService.getUsersByLastName(lastName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-last-name-index")
    public ResponseEntity<List<JunitUser>> getUsersByLastNameWithIndexParameter(@RequestParam String lastName) {
        List<JunitUser> users = junitUserService.getUsersByLastNameWithIndexParameter(lastName);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/by-role")
    public ResponseEntity<List<UserDto>> getUsersByRole(@RequestParam String roleName) {
        return ResponseEntity.ok(junitUserService.getUsersByRoleName(roleName));
    }
}

