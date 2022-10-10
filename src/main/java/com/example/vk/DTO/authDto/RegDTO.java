package com.example.vk.DTO.authDto;


import lombok.Data;

@Data
public class RegDTO {

    private String email;
    private String password;
    private String twoPassword;
    private String name;
    private String surname;
}