package com.example.vk.Facade;


import com.example.vk.Controllers.Funchional.UserF;
import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.User;
import com.example.vk.Exeption.NotFoundException;
import com.example.vk.Mapper.UserDtoMapper;
import com.example.vk.Service.Implaye.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserFacade {

    private final UserServiceImp userServiceImp;

    private final UserDtoMapper userDtoMapper;

    @Autowired
    public UserFacade(UserServiceImp userServiceImp, UserDtoMapper userDtoMapper) {
        this.userServiceImp = userServiceImp;
        this.userDtoMapper = userDtoMapper;
    }

    public UserDTO getUser(Long id){
        if(id == null){
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        User user = userServiceImp.getUserById(id);
        if(user == null){
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        log.info("User found with id:{}", id);
        return userDtoMapper.userToUserDTO(user);
    }

    public UserDTO updateProfile(Long id, UserDTO userDTO){
        if(id == null || userDTO == null){
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        userDTO.setId(id);
        User user = userServiceImp.updateUser(userDtoMapper.userDTOToUser(userDTO));
        log.info("User update :{}", user);
        return userDtoMapper.userToUserDTO(user);
    }

    public List<UserDTO> getUserInRadius(Long from, Long to){
        if(from == null || to == null){
            throw new NotFoundException("Users in radius not found");
        }
        log.info("User get in radius from {} to {}", from, to);
        return userServiceImp.getUserInRadius(from, to)
                .stream()
                .filter(Objects::nonNull)
                .map(userDtoMapper::userToUserDTO)
                .peek(n -> log.info("In radius user {}", n))
                .collect(Collectors.toList());

    }

    public void deleteUserById(Long userId){
        if(userId == null){
            throw new NotFoundException("User not found");
        }
        log.info("Delete user with id {}", userId);
        userServiceImp.deleteUserById(userId);
        log.info("Delete suggest");
    }
}
