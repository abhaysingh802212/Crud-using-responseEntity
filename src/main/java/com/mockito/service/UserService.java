package com.mockito.service;


import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

 UserDto createUser(UserDto userDto);
 List<UserDto> getAllUser();
 UserDto getUserById(Long id);
void deleteById(Long id);

 List<UserDto> getAllActiveUsers();


}
