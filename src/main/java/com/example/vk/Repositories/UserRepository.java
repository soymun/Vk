package com.example.vk.Repositories;

import com.example.vk.Entity.Dialog;
import com.example.vk.Entity.Post;
import com.example.vk.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    Optional<User> findUserById(Long id);

}
