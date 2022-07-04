package com.example.vk.Service.Implaye;

import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.*;
import com.example.vk.Repositories.UserRepository;
import com.example.vk.Service.UserService;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImp implements UserService {


    @PersistenceContext
    EntityManager entityManager;

    UserRepository userRepository;
    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null || username.equals("")){
            throw new IllegalArgumentException("Email is null");
        }
        User user = getUserByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRoles());
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

    public List<UserDTO> getFollow(Long id){
        List<UserDTO> userDTOS = new ArrayList<>();
        new JPAQuery<User>(entityManager)
                .select(QUser.user)
                .from(QUser.user)
                .where(QUser.user.id.in(new JPAQuery<Follow>(entityManager)
                        .select(QFollow.follow.userTwo)
                        .from(QFollow.follow)
                        .where(QFollow.follow.userId.eq(id))
                        .fetchAll()))
                .fetch().forEach(n -> userDTOS.add(new UserDTO(n)));
        return userDTOS;
    }

    public List<Post> getNews(Long id, Long skip, Long limit){
        List<Post> userDTOS = new ArrayList<>();
        new JPAQuery<User>(entityManager)
                .select(QUser.user.posts)
                .from(QUser.user)
                .where(QUser.user.id.in(new JPAQuery<Follow>(entityManager)
                        .select(QFollow.follow.userTwo)
                        .from(QFollow.follow)
                        .where(QFollow.follow.userId.eq(id))
                        .fetchAll()))
                .fetch().forEach(userDTOS::addAll);
        return  userDTOS.stream().skip(skip).limit(limit).collect(Collectors.toList());
    }
}
