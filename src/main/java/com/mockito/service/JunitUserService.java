package com.mockito.service;


import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;
import com.mockito.repository.JunitUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JunitUserService {

    @Autowired
    private JunitUserRepository junitUserRepository;

    public List<JunitUser> getUsersByFirstName(String firstName) {
        return junitUserRepository.findByFirstName(firstName);
    }

    public List<JunitUser> getUsersByFirstNameAndLastName(String firstName, String lastName) {
        return junitUserRepository.findByFirstNameWithIndexParameter(firstName, lastName);
    }

    public List<JunitUser> getUsersByLastName(String lastName) {
        return junitUserRepository.findByLastName(lastName);
    }

    public List<JunitUser> getUsersByLastNameWithIndexParameter(String lastName) {
        return junitUserRepository.findByLastName(lastName);
    }

    public List<UserDto> getUsersByRoleName(String roleName) {
        List<Object[]> results = junitUserRepository.findUsersByRoleName(roleName);
        return results.stream()
                .map(row -> new UserDto(
                        ((Number) row[0]).longValue(),
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]))
                .collect(Collectors.toList());
    }
}

