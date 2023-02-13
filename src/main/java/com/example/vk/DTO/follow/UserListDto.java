package com.example.vk.DTO.follow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {

    private Long id;

    private String name;

    private String surname;

    private String about;

    private String patronymic;

    private String urlToAvatar;
}
