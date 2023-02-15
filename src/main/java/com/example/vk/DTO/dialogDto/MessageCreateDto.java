package com.example.vk.DTO.dialogDto;

import lombok.Data;

@Data
public class MessageCreateDto {

    private Long dialogId;

    private Long userId;

    private String textMessage;
}
