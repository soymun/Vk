package com.example.vk.Service;

import com.example.vk.DTO.dialogDto.UserMessageDto;
import com.example.vk.DTO.follow.FromToUser;
import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.DTO.newsDto.News;
import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User getUserByUsername(String username);

    UserDTO getUserById(Long id);


    void save(User user);


    UserDTO updateUser(UserDTO updateUser);


    List<UserListDto> getUserInRadius(FromToUser fromToUser);


    void deleteUserById(Long id);

    List<UserListDto> getFollow(Long id);

    List<UserMessageDto> getUserByDialogId(Long dialogId);

    List<News> getNews(Long id, Long page);
}
