package com.example.vk.Service.Implaye;

import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.*;
import com.example.vk.Exeption.NotFoundException;
import com.example.vk.Repositories.UserRepository;
import com.example.vk.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
@Slf4j
public class UserServiceImp implements UserService {


    @PersistenceContext
    EntityManager entityManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.equals("")){
            throw new IllegalArgumentException("Email is null");
        }
        User user = getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRole().getAuthority());
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByEmail(username);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Transactional
    @Override
    public void save(User user){
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User updateUser) {
        User user = getUserById(updateUser.getId());
        if (user  == null){
            throw new NotFoundException(String.format("User with id:%s not found", updateUser.getId()));
        }
        else {
            if(updateUser.getEmail() != null){
                user.setEmail(updateUser.getEmail());
            }
            if(updateUser.getAbout() != null){
                user.setAbout(updateUser.getAbout());
            }
            if(updateUser.getPassword() != null){
                user.setPassword(passwordEncoder.encode(updateUser.getEmail()));
            }
            if(updateUser.getName() != null){
                user.setName(updateUser.getName());
            }
            if(updateUser.getSurname() != null){
                user.setSurname(updateUser.getSurname());
            }
        }
        save(user);
        return user;
    }

    @Override
    public List<User> getUserInRadius(Long from, Long to) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    public List<UserDTO> getFollow(Long id){
        return null;
    }

    public List<Post> getNews(Long id, Long skip, Long limit){
        return  null;
    }
}
