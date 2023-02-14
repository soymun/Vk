package com.example.vk.Service.Implaye;


import com.example.vk.DTO.follow.FollowDto;
import com.example.vk.DTO.follow.UserListDto;
import com.example.vk.Entity.Follow;
import com.example.vk.Entity.Follow_;
import com.example.vk.Entity.User;
import com.example.vk.Entity.User_;
import com.example.vk.Mapper.FollowMapper;
import com.example.vk.Repositories.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    private final FollowMapper followMapper;

    @PersistenceContext
    EntityManager entityManager;

    public void saveFollow(FollowDto follow){
        log.info("Сохранение подписки");
        followRepository.save(followMapper.followDtoToFollow(follow));
    }

    public void deleteFollow(Long followId){
        log.info("Удаление подписки");
        followRepository.deleteById(followId);
    }

    public List<UserListDto> getFollow(Long id) {

        log.info("Выдача подписчиков");

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
                root.get(User_.about),
                root.get(User_.patronymic),
                root.get(User_.urlToAvatar)
        );

        return entityManager.createQuery(cq).getResultList();
    }
}
