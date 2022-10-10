package com.example.vk.DTO.follow;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserListDto {

    private Long id;
    private String name;

    private String surname;

    private String about;

}
