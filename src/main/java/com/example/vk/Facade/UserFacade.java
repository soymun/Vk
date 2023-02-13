package com.example.vk.Facade;


import com.example.vk.DTO.PostDto;
import com.example.vk.DTO.follow.FromToUser;
import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.Entity.Post;
import com.example.vk.Mapper.UserDtoMapper;
import com.example.vk.Repositories.PostRepository;
import com.example.vk.Response.ResponseDto;
import com.example.vk.Service.Implaye.UserServiceImp;
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

    private final PostRepository postRepository;

    public ResponseEntity<?> getUser(Long id) {
        if (id == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        UserDTO userDTO = userServiceImp.getUserById(id);
        if (userDTO == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        log.info("User found with id:{}", id);
        userDTO.setPosts(postRepository.getPostByUserId(userDTO.getId()).stream().map(userDtoMapper::postToUserPostDto).collect(Collectors.toList()));
        return ResponseEntity.ok(ResponseDto.builder().data(userDTO).build());
    }

    public ResponseEntity<?> updateProfile(Long id, UserDTO userDTO) {
        if (id == null || userDTO == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        userDTO.setId(id);
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

    public ResponseEntity<?> createPost(PostDto postDto) {
        log.info("Create post :{}", postDto);
        Post post = new Post();
        post.setUserId(postDto.getUserId());
        post.setText(postDto.getText());
        post.setLikes(0L);
        post.setDisLikes(0L);
        post.setTimePost(LocalDate.now());
        Post savedPost = postRepository.save(post);
        log.info("Saved new post {}", savedPost);
        return ResponseEntity.ok(ResponseDto.builder().data(post).build());
    }

    public ResponseEntity<?> addLike(Long postId){
        log.info("Add like in post :{}", postId);
        Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("Post not found"));
        post.setLikes(post.getLikes()+1);
        Post savedPost = postRepository.save(post);
        log.info("Post add like");
        return ResponseEntity.ok(ResponseDto.builder().data(savedPost).build());
    }

    public ResponseEntity<?> addDislikes(Long postId){
        log.info("Add dislikes in post :{}", postId);
        Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("Post not found"));
        post.setDisLikes(post.getDisLikes()+1);
        Post savedPost = postRepository.save(post);
        log.info("Post add dislike");
        return ResponseEntity.ok(ResponseDto.builder().data(savedPost).build());
    }

    public ResponseEntity<?> getNews(Long id, Long page){
        log.info("Get news by user {}", id);
        return ResponseEntity.ok(ResponseDto.builder().data(userServiceImp.getNews(id, page)).build());
    }
}
