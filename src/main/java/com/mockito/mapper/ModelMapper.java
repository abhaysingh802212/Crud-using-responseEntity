package com.mockito.mapper;

import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;

public class ModelMapper {

    public static UserDto mapToUserDto(JunitUser user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
    public static JunitUser mapToUser(UserDto userDto){
        JunitUser user = new JunitUser();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        return user;
    }
}
