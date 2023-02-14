package com.example.vk.Service.Implaye;

import com.example.vk.DTO.dialogDto.UserMessageDto;
import com.example.vk.DTO.follow.FromToUser;
import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.DTO.newsDto.News;
import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.Entity.*;
import com.example.vk.Entity.Follow_;
import com.example.vk.Entity.Post_;
import com.example.vk.Entity.UserDialog_;
import com.example.vk.Entity.User_;
import com.example.vk.Mapper.UserDtoMapper;
import com.example.vk.Repositories.UserRepository;
import com.example.vk.Service.UserService;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {


    @PersistenceContext
    EntityManager entityManager;

    private final UserRepository userRepository;

    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.equals("")) {
            throw new IllegalArgumentException("Email is null");
        }
        User user = getUserByUsername(username);
        if (user == null) {
            throw new NotFoundException(String.format("User with email %s not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getRole().getAuthority());
    }

    @Override
    public User getUserByUsername(String username) {
        log.info("Выдача пользователя по имени {}", username);
        return userRepository.findUserByEmail(username);
    }

    @Override
    public UserDTO getUserById(Long id) {
        log.info("Выдача пользователя по id {}", id);
        return userDtoMapper.userToUserDTO(userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Пользователь не найден");
        }));
    }


    @Override
    public void save(User user) {
        log.info("Сохранение пользователя с именем {}", user.getName());
        userRepository.save(user);
    }

    @Override
    public UserDTO updateUser(UserDTO updateUser) {
        log.info("Изменение польхователя с id {}", updateUser.getId());
        User user = userRepository.findUserById(updateUser.getId()).orElseThrow(() -> {
            throw new NotFoundException("Пользователь не найден");
        });
        if (updateUser.getEmail() != null) {
            user.setEmail(updateUser.getEmail());
        }
        if (updateUser.getRole() != null) {
            user.setRole(updateUser.getRole());
        }
        if (updateUser.getName() != null) {
            user.setName(updateUser.getName());
        }
        if (updateUser.getSurname() != null) {
            user.setSurname(updateUser.getSurname());
        }
        if (updateUser.getPatronymic() != null) {
            user.setPatronymic(updateUser.getPatronymic());
        }
        if (updateUser.getUrlToAvatar() != null) {
            user.setUrlToAvatar(updateUser.getUrlToAvatar());
        }
        if (updateUser.getLink() != null) {
            user.setLink(updateUser.getLink());
        }
        if (updateUser.getBirthday() != null) {
            user.setBirthday(updateUser.getBirthday());
        }
        if (updateUser.getAbout() != null) {
            user.setAbout(updateUser.getAbout());
        }
        return userDtoMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public List<UserListDto> getUserInRadius(FromToUser fromToUser) {

        log.info("Выдача пользователей по поиску");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserListDto> cq = cb.createQuery(UserListDto.class);
        Root<User> root = cq.from(User.class);
        Predicate predicate = cb.not(cb.equal(root.get(User_.ID), fromToUser.getUserId()));
        if (fromToUser.getName() != null) {
            predicate = cb.and(predicate, cb.equal(root.get(User_.name), fromToUser.getName()));
        }
        if (fromToUser.getSurname() != null) {
            predicate = cb.and(predicate, cb.equal(root.get(User_.surname), fromToUser.getSurname()));
        }
        if (fromToUser.getPatronymic() != null) {
            predicate = cb.and(predicate, cb.equal(root.get(User_.patronymic), fromToUser.getPatronymic()));
        }
        cq.where(predicate);

        cq.multiselect(
                root.get(User_.id),
                root.get(User_.name),
                root.get(User_.surname),
                root.get(User_.about),
                root.get(User_.patronymic),
                root.get(User_.urlToAvatar)
        );

        return entityManager.createQuery(cq)
                .setFirstResult(((fromToUser.getPage().intValue() - 1) * 30))
                .setMaxResults(fromToUser.getPage().intValue() * 30)
                .getResultList();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {

        log.info("Удаление пользователя");

        userRepository.deleteById(id);
    }

    @Override
    public List<UserMessageDto> getUserByDialogId(Long dialogId) {

        log.info("Выдача пользователей в диалоге c id {}", dialogId);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserMessageDto> cq = cb.createQuery(UserMessageDto.class);
        Root<UserDialog> root = cq.from(UserDialog.class);

        Join<UserDialog, User> join = root.join(UserDialog_.USER);

        cq.where(cb.equal(root.get(UserDialog_.DIALOG_ID), dialogId));

        cq.multiselect(
                join.get(User_.id),
                join.get(User_.NAME),
                join.get(User_.surname),
                join.get(User_.patronymic),
                join.get(User_.urlToAvatar)
        );

        return entityManager
                .createQuery(cq)
                .getResultList();
    }
}
