package com.example.vk.DTO.follow;


import lombok.Data;

@Data
public class FromToUser {

    private Long userId;

    private Long page;

    private String name;

    private String surname;

    private String patronymic;
}
