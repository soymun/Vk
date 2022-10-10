package com.example.vk.DTO.dialogDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class MessageResponseDto {

    private Long userId;

    private Long dialogId;

    private Date timePost;

    private String text;

    public MessageResponseDto(Long userId, Date timePost, String text) {
        this.userId = userId;
        this.timePost = timePost;
        this.text = text;
    }
}
