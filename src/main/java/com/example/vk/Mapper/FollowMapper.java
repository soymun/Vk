package com.example.vk.Mapper;

import com.example.vk.DTO.follow.FollowDto;
import com.example.vk.Entity.Follow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FollowMapper {

    Follow followDtoToFollow(FollowDto followDto);
}
