package com.example.vk.DTO.profileDto;

import lombok.Data;

import java.util.Date;

@Data
public class UserPostDto {

    private Long id;

    private Long userId;

    private Date timePost;

    private String text;

    private Long likes;

    private Long disLikes;
}
