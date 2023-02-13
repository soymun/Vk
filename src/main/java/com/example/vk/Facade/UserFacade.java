package com.example.vk.Facade;


import com.example.vk.DTO.PostDto;
import com.example.vk.DTO.newsDto.News;
import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.Entity.Post;
import com.example.vk.Entity.User;
import com.example.vk.Mapper.UserDtoMapper;
import com.example.vk.Repositories.PostRepository;
import com.example.vk.Response.PostDtoResponse;
import com.example.vk.Service.Implaye.UserServiceImp;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceImp userServiceImp;

    private final UserDtoMapper userDtoMapper;

    private final PostRepository postRepository;

    public UserDTO getUser(Long id) {
        if (id == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        User user = userServiceImp.getUserById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        log.info("User found with id:{}", id);
        UserDTO userDTO = userDtoMapper.userToUserDTO(user);
        userDTO.setPosts(postRepository.getPostByUserId(userDTO.getId()).stream().map(userDtoMapper::postToUserPostDto).collect(Collectors.toList()));
        return userDTO;
    }

    public UserDTO updateProfile(Long id, UserDTO userDTO) {
        if (id == null || userDTO == null) {
            throw new NotFoundException(String.format("User with id:%s not found", id));
        }
        userDTO.setId(id);
        User user = userServiceImp.updateUser(userDtoMapper.userDTOToUser(userDTO));
        log.info("User update :{}", user);
        return userDtoMapper.userToUserDTO(user);
    }

    public List<UserDTO> getUserInRadius(Long userId, Long from, Long to) {
        if (from == null || to == null) {
            throw new NotFoundException("Users in radius not found");
        }
        log.info("User get in radius from {} to {}", from, to);

        return userServiceImp.getUserInRadius(userId, from, to).stream().filter(Objects::nonNull).map(userDtoMapper::userListDtoToUserDTO).peek(n -> log.info("In radius user {}", n)).collect(Collectors.toList());

    }

    public void deleteUserById(Long userId) {
        if (userId == null) {
            throw new NotFoundException("User not found");
        }
        log.info("Delete user with id {}", userId);
        userServiceImp.deleteUserById(userId);
        log.info("Delete suggest");
    }

    public PostDtoResponse createPost(PostDto postDto) {
        log.info("Create post :{}", postDto);
        Post post = new Post();
        post.setUserId(postDto.getUserId());
        post.setText(postDto.getText());
        post.setLikes(0L);
        post.setTimePost(LocalDate.now());
        Post savedPost = postRepository.save(post);
        log.info("Saved new post {}", savedPost);
        return PostDtoResponse.builder().timePost(savedPost.getTimePost()).id(savedPost.getId()).likes(savedPost.getLikes()).text(savedPost.getText()).userId(savedPost.getUserId()).build();
    }

    public PostDtoResponse addLike(Long postId){
        log.info("Add like in post :{}", postId);
        Post post = postRepository.findById(postId).orElseThrow(()-> new NotFoundException("Post not found"));
        post.setLikes(post.getLikes()+1);
        Post savedPost = postRepository.save(post);
        log.info("Post add like");
        return PostDtoResponse.builder().timePost(savedPost.getTimePost()).id(savedPost.getId()).likes(savedPost.getLikes()).text(savedPost.getText()).userId(savedPost.getUserId()).build();
    }

    public List<News> getNews(Long id, Long page){
        log.info("Get news by user {}", id);
        return userServiceImp.getNews(id, page);
    }
}
