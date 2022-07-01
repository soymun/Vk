package com.example.vk.DTO;

import com.example.vk.Entity.Post;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private String name;

    private String surname;

    private String about;

    private List<Post> posts;
}
