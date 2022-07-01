package com.example.vk.Repositories.Imp;

import com.example.vk.Entity.Post;
import com.example.vk.Entity.Role;
import com.example.vk.Entity.User;
import com.example.vk.Service.Implaye.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserRepoImpTest {

    @Autowired
    UserServiceImp userServiceImp;

    @Test
    void getUserByUsername() {
        User user = userServiceImp.getUserByUsername("kekep");
        assertEquals(user.getRoles().get(0), "USER");
    }

    @Test
    void getUserById() {
        userServiceImp.save(new User(3L, "1", "1", "1", "1", null,null,List.of(Role.USER),null));
    }

    @Test
    void addUser() {
    }
}