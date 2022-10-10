package com.example.vk.Response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PostDtoResponse {

    private Long id;

    private Long userId;

    private Date timePost;

    private String text;

    private Long likes;
}
