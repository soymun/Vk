package com.example.vk.Facade;


import com.example.vk.DTO.postsDto.PostDto;
import com.example.vk.DTO.follow.FromToUser;
import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.Entity.Post;
import com.example.vk.Mapper.UserDtoMapper;
import com.example.vk.Repositories.PostRepository;
import com.example.vk.Response.ResponseDto;
import com.example.vk.Service.Implaye.PostServiceImpl;
import com.example.vk.Service.Implaye.UserServiceImp;
import com.example.vk.Service.PostService;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceImp userServiceImp;

    private final UserDtoMapper userDtoMapper;

    private final PostServiceImpl postService;

    public ResponseEntity<?> getUser(Long id) {
        if (id == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        UserDTO userDTO = userServiceImp.getUserById(id);
        if (userDTO == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        log.info("User found with id:{}", id);
        userDTO.setPosts(postService.getPostByUserId(userDTO.getId()));
        return ResponseEntity.ok(ResponseDto.builder().data(userDTO).build());
    }

    public ResponseEntity<?> updateProfile(UserDTO userDTO) {
        if (userDTO == null) {
            throw new NotFoundException("User with id not found");
        }
        UserDTO user = userServiceImp.updateUser(userDTO);
        log.info("User update :{}", user);
        return ResponseEntity.ok(ResponseDto.builder().data(user).build());
    }

    public ResponseEntity<?> getUserInRadius(FromToUser fromToUser) {
        if (fromToUser == null) {
            throw new NotFoundException("Users not found");
        }

        log.info("Get users");

        return ResponseEntity
                .ok(ResponseDto.builder()
                        .data(userServiceImp.getUserInRadius(fromToUser)
                                .stream()
                                .filter(Objects::nonNull)
                                .map(userDtoMapper::userListDtoToUserDTO)
                                .collect(Collectors.toList()))
                        .build());

    }

    public void deleteUserById(Long userId) {
        if (userId == null) {
            throw new NotFoundException("User not found");
        }
        log.info("Delete user with id {}", userId);
        userServiceImp.deleteUserById(userId);
        log.info("Delete suggest");
    }
}
