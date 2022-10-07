package com.example.vk.Mapper;

import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
