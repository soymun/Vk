package com.example.vk.DTO.dialogDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MessageDialogDto {

    private Long id;

    private Long dialogId;

    private Long userId;

    private String name;

    private String surname;

    private String patronymic;

    private String urlToAvatar;

    private String link;

    private String textMessage;

    private LocalDate timePost;
}
