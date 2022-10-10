package com.example.vk.Service;

import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    @Transactional
    User getUserByUsername(String username);

    @Transactional
    User getUserById(Long id);

    @Transactional
    void save(User user);

    @Transactional
    List<User> findAll();

    @Transactional
    User updateUser(User updateUser);

    @Transactional
    List<UserListDto> getUserInRadius(Long userId,Long from, Long to);

    @Transactional
    void deleteUserById(Long id);
}
