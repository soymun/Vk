package com.example.vk.Service.Implaye;

import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.DTO.dialogDto.UserMessageDto;
import com.example.vk.DTO.newsDto.News;
import com.example.vk.Entity.*;
import com.example.vk.Entity.Follow_;
import com.example.vk.Entity.Post_;
import com.example.vk.Entity.UserDialog_;
import com.example.vk.Entity.User_;
import com.example.vk.Mapper.UserDtoMapper;
import com.example.vk.Repositories.PostRepository;
import com.example.vk.Repositories.UserRepository;
import com.example.vk.Service.UserService;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
        if(username == null || username.equals("")){
            throw new IllegalArgumentException("Email is null");
        }
        User user = getUserByUsername(username);
        if(user == null){
            throw new NotFoundException(String.format("User with email %s not found", username));
        }
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


    @Override
    public void save(User user){
        userRepository.save(user);
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
                user.setPassword(updateUser.getEmail());
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
    public List<UserListDto> getUserInRadius(Long userId, Long from, Long to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserListDto> cq = cb.createQuery(UserListDto.class);
        Root<User> root = cq.from(User.class);
        cq.where(cb.not(cb.equal(root.get(User_.ID), userId)));
        cq.multiselect(
                root.get(User_.id),
                root.get(User_.name),
                root.get(User_.surname),
                root.get(User_.about)
        );
        return entityManager.createQuery(cq).setFirstResult(from.intValue()).setMaxResults(to.intValue()).getResultList();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserListDto> getFollow(Long id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserListDto> cq = cb.createQuery(UserListDto.class);
        Root<User> root = cq.from(User.class);

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Follow> roots = subquery.from(Follow.class);
        subquery.select(
                roots.get(Follow_.userTwo)
        );
        subquery.where(cb.equal(roots.get(Follow_.userOne), id));
        cq.where(cb.in(root.get(User_.id)).value(subquery));

        cq.multiselect(
                root.get(User_.id),
                root.get(User_.name),
                root.get(User_.surname),
                root.get(User_.about)
        );

        return entityManager.createQuery(cq).getResultList();
    }

    public List<UserMessageDto> getUserByDialogId(Long dialogId){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserMessageDto> cq = cb.createQuery(UserMessageDto.class);
        Root<UserDialog> root = cq.from(UserDialog.class);

        Join<UserDialog, User> join = root.join(UserDialog_.USER);

        cq.where(cb.equal(root.get(UserDialog_.DIALOG_ID), dialogId));

        cq.multiselect(
                join.get(User_.id),
                join.get(User_.NAME),
                join.get(User_.surname)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    public List<News> getNews(Long id, Long page){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> cq = cb.createQuery(News.class);
        Root<Post> root = cq.from(Post.class);
        Join<Post, User> join = root.join(Post_.USER);
        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Follow> roots = subquery.from(Follow.class);
        subquery.where(cb.equal(roots.get(Follow_.USER_ONE), id));
        subquery.select(
                roots.get(Follow_.USER_TWO)
        );
        cq.where(cb.in(join.get(User_.ID)).value(subquery));
        cq.multiselect(
            root.get(Post_.ID),
                root.get(Post_.USER_ID),
                root.get(Post_.TIME_POST),
                root.get(Post_.TEXT),
                root.get(Post_.LIKES)
        );
        return entityManager.createQuery(cq)
                .setFirstResult((page.intValue()-1)*100)
                .setMaxResults(page.intValue()*100)
                .getResultList();
    }
}
