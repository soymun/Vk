package com.example.vk.Mapper;

import com.example.vk.DTO.postsDto.PostDto;
import com.example.vk.DTO.profileDto.UserPostDto;
import com.example.vk.Entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    UserPostDto postToUserPostDto(Post post);

    Post postDtoToPost(PostDto postDto);

    PostDto postToPostDto(Post post);
}
