package com.example.vk.DTO.authDto;

import lombok.Data;

@Data
public class ReLoginDto {

    private Long userId;

    private String token;

    private boolean getWithNotValid;
}
