package com.example.vk.Controllers;


import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.Follow;
import com.example.vk.Repositories.FollowRepository;
import com.example.vk.Service.Implaye.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vk")
public class FollowUserController {

    @Autowired
    private UserServiceImp userServiceImp;
    private FollowRepository followRepository;

    @GetMapping("/feed/{id}")
    public List<UserDTO> getFollow(@PathVariable Long id){
        return userServiceImp.getFollow(id);
    }

    @PostMapping("/feed/{id}")
    public void setUser(@RequestBody Long userTwo, @PathVariable Long id){
        Follow follow = new Follow(id, userTwo);
        followRepository.save(follow);
    }

    @DeleteMapping("/feed")
    public void unFollow(@RequestBody Long followId){
        Follow follow = followRepository.findFollowById(followId);
        followRepository.delete(follow);
    }
}
