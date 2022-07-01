package com.example.vk.Service;

import com.example.vk.Entity.Dialog;
import com.example.vk.Entity.Post;
import com.example.vk.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User getUserByUsername(String username);

    User getUserById(Long id);

    @Transactional
    void save(User user);
}
