package com.example.vk.Service;

import com.example.vk.DTO.follow.FollowDto;
import com.example.vk.DTO.follow.UserListDto;

import java.util.List;

public interface FollowServiceIntr {

    List<UserListDto> getFollow(Long id);

    void deleteFollow(Long followId);

    void saveFollow(FollowDto follow);
}
