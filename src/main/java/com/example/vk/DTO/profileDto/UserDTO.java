package com.example.vk.DTO.profileDto;

import com.example.vk.Entity.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String email;

    private String password;

    private Role role;

    private String name;

    private String urlToAvatar;

    private String surname;

    private String patronymic;

    private String about;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;

    private String link;

    private List<UserPostDto> posts;
}
