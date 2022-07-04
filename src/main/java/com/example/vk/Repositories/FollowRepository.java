package com.example.vk.Repositories;

import com.example.vk.Entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findFollowById(Long id);
}
