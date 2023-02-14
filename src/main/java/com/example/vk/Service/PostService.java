package com.example.vk.Service;

import com.example.vk.DTO.postsDto.PostDto;
import com.example.vk.DTO.newsDto.News;
import com.example.vk.DTO.postsDto.PostUpdateDto;
import com.example.vk.DTO.profileDto.UserPostDto;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    List<UserPostDto> getPostByUserId(Long userId);

    List<News> getNews(Long userId, Long page);

    PostDto updatePost(PostUpdateDto postUpdateDto);

    void deletePost(Long id);
}
