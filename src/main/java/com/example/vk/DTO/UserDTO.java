package com.example.vk.DTO;

import com.example.vk.Entity.Post;
import com.example.vk.Entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.about = user.getAbout();
        this.posts = user.getPosts();
    }

    private Long id;
    private String name;

    private String surname;

    private String about;

    private List<Post> posts;
}
