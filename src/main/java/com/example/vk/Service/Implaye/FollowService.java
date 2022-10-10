package com.example.vk.Service.Implaye;


import com.example.vk.Entity.Follow;
import com.example.vk.Repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    private final FollowRepository followRepository;

    @Autowired
    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void saveFollow(Follow follow){
        followRepository.save(follow);
    }

    public void deleteFollow(Long followId){
        followRepository.deleteById(followId);
    }
}
