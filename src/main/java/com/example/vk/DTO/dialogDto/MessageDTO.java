package com.example.vk.DTO.dialogDto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {

    private Long id;

    private Long dialogId;

    private Long userId;

    private String textMessage;
}
