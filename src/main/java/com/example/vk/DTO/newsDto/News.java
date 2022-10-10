package com.example.vk.DTO.newsDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class News {

    private Long id;

    private Long userId;

    private Date tamePost;

    private String text;

    private Long likes;
}
