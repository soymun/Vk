package com.example.vk.DTO.newsDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
public class News {

    private Long id;

    private Long userId;

    private LocalDate tamePost;

    private String text;

    private Long likes;

    private Long disLikes;
}
