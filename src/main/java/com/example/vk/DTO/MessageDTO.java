package com.example.vk.DTO;


import lombok.Data;

@Data
public class MessageDTO {

    private Long dialogId;

    private Long userId;

    private String textMessage;
}
