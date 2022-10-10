package com.example.vk.Controllers;


import com.example.vk.DTO.follow.FollowDto;
import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.Facade.FollowFacade;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vk")
@Slf4j
@CrossOrigin(origins="http://localhost:3000")
public class FollowUserController {

    private final FollowFacade followFacade;


    @Autowired
    public FollowUserController(FollowFacade findFollowById) {
        this.followFacade = findFollowById;
    }

    @GetMapping("/feed/{id}")
    public List<UserListDto> getFollow(@PathVariable Long id){
        if (id == null){
            throw new NotFoundException("Id is null");
        }
        log.info("Get follow");
        return followFacade.getFollowUser(id);
    }

    @PostMapping("/feed")
    public void setUser(@RequestBody FollowDto followDto){
        if (followDto == null){
            throw new NotFoundException("Don't save follow");
        }
        log.info("Follow user");
        followFacade.saveFollow(followDto);
    }

    @DeleteMapping("/feed/{id}")
    public void unFollow(@PathVariable Long id){
        log.info("Unfollow user");
        followFacade.unFollow(id);
    }
}
