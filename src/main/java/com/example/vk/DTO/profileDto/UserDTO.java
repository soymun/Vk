package com.example.vk.DTO.profileDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;

    private String surname;

    private String about;

    private List<UserPostDto> posts;
}
