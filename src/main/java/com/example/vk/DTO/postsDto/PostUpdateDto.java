package com.example.vk.DTO.postsDto;

import lombok.Data;

@Data
public class PostUpdateDto {

    private Long id;

    private String text;

    private Long likes;

    private Long disLikes;
}
