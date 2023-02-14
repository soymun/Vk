package com.example.vk.Controllers;


import com.example.vk.DTO.follow.FollowDto;
import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.Service.Implaye.FollowService;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/feed")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:3000")
public class FollowUserController {

    private final FollowService followService;

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public List<UserListDto> getFollow(@PathVariable Long id){
        if (id == null){
            throw new NotFoundException("Id is null");
        }
        log.info("Get follow");
        return followService.getFollow(id);
    }

    @PostMapping("/feed")
    @PreAuthorize(value = "hasAuthority('USER')")
    public void setUser(@RequestBody FollowDto followDto){
        if (followDto == null){
            throw new NotFoundException("Don't save follow");
        }
        log.info("Follow user");
        followService.saveFollow(followDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public void unFollow(@PathVariable Long id){
        log.info("Unfollow user");
        followService.deleteFollow(id);
    }
}
