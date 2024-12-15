package com.mockito.service.impl;

import com.mockito.domain.JunitUser;
import com.mockito.dto.UserDto;
import com.mockito.mapper.ModelMapper;
import com.mockito.repository.JunitUserRepository;
import com.mockito.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private JunitUserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {

        try {
            JunitUser user = new JunitUser(userDto);
            log.info("User data save in the databases");
            user = userRepository.save(user);
            return ModelMapper.mapToUserDto(user);

        } catch (Exception e) {
            throw new RuntimeException("Failed to store user data in the database"+e.getMessage());
        }
    }

    @Override
    public List<UserDto> getAllUser() {
       // try {
            List<JunitUser> user = userRepository.findAll();
            return user.stream()
                    .map(ModelMapper::mapToUserDto)
                    .collect(Collectors.toList());
//        } catch (Exception e) {
//            throw new RuntimeException("Users not found: " + e.getMessage());
//        }
        }


    @Override
    public UserDto getUserById(Long id) {
        //try {
            JunitUser user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
            return ModelMapper.mapToUserDto(user);
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred while fetching the user: " + e.getMessage());
//        }
    }

    @Override
    public void deleteById(Long id) {
       // try {
            userRepository.deleteById(id);
//        } catch (Exception e) {
//            throw new RuntimeException("An error occurred while fetching the user: " + e.getMessage());
//        }
    }

    @Override
    public List<UserDto> getAllActiveUsers() {
        List<Object[]> userDtos = userRepository.findAllActiveUsers();
        return userDtos.stream()
                .map(obj -> new UserDto(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],
                        (String) obj[2],
                        (String) obj[3],
                        (String) obj[4]
                ))
                .collect(Collectors.toList());
    }


}
