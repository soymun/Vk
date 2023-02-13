package com.example.vk.DTO.dialogDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMessageDto {

    private Long id;

    private String name;

    private String surname;

    private String patronymic;

    private String urlToAvatar;
}
