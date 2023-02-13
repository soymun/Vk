package com.example.vk.Response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
public class PostDtoResponse {

    private Long id;

    private Long userId;

    private LocalDate timePost;

    private String text;

    private Long likes;
}
