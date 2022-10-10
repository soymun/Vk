package com.example.vk.Facade;


import com.example.vk.DTO.follow.FollowDto;
import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.Mapper.FollowMapper;
import com.example.vk.Service.Implaye.FollowService;
import com.example.vk.Service.Implaye.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FollowFacade {

    private final UserServiceImp userServiceImp;

    private final FollowMapper followMapper;
    private final FollowService followService;

    @Autowired
    public FollowFacade(UserServiceImp userServiceImp, FollowMapper followMapper, FollowService followService) {
        this.userServiceImp = userServiceImp;
        this.followMapper = followMapper;
        this.followService = followService;
    }

    public List<UserListDto> getFollowUser(Long id){
        return userServiceImp.getFollow(id)
                .stream()
                .filter(Objects::nonNull)
                .peek(n -> log.info("Follow user {}", n))
                .collect(Collectors.toList());
    }

    public void saveFollow(FollowDto followDto){
        log.info("Save follow {}", followDto);
        followService.saveFollow(followMapper.followDtoToFollow(followDto));
    }

    public void unFollow(Long followId){
        log.info("Unfollow user with id {}", followId);
        followService.deleteFollow(followId);
    }
}
