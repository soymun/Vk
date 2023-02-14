package com.example.vk.Mapper;

import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.DTO.profileDto.UserPostDto;
import com.example.vk.Entity.Post;
import com.example.vk.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);

    UserDTO userListDtoToUserDTO(UserListDto userListDto);
}
