package com.example.vk.Service.Implaye;

import com.example.vk.DTO.newsDto.News;
import com.example.vk.DTO.postsDto.PostDto;
import com.example.vk.DTO.postsDto.PostUpdateDto;
import com.example.vk.DTO.profileDto.UserPostDto;
import com.example.vk.Entity.*;
import com.example.vk.Entity.Follow_;
import com.example.vk.Entity.Post_;
import com.example.vk.Entity.User_;
import com.example.vk.Mapper.PostMapper;
import com.example.vk.Repositories.PostRepository;
import com.example.vk.Service.PostService;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @PersistenceContext
    EntityManager entityManager;


    @Override
    public PostDto savePost(PostDto postDto) {
        log.info("Create post :{}", postDto);
        Post post = new Post();
        post.setUserId(postDto.getUserId());
        post.setText(postDto.getText());
        post.setLikes(0L);
        post.setDisLikes(0L);
        post.setTimePost(LocalDate.now());
        return postMapper.postToPostDto(postRepository.save(post));
    }

    @Override
    public List<UserPostDto> getPostByUserId(Long userId) {
        log.info("Выдача постов пользователя с id {}", userId);
        return postRepository.getPostByUserId(userId).stream().map(postMapper::postToUserPostDto).collect(Collectors.toList());
    }

    @Override
    public List<News> getNews(Long id, Long page) {
        log.info("Выдача новостей");

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<News> cq = cb.createQuery(News.class);
        Root<Post> root = cq.from(Post.class);
        Join<Post, User> join = root.join(Post_.USER);

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Follow> roots = subquery.from(Follow.class);
        subquery.where(cb.equal(roots.get(com.example.vk.Entity.Follow_.USER_ONE), id));
        subquery.select(
                roots.get(Follow_.USER_TWO)
        );

        cq.where(cb.in(join.get(User_.ID)).value(subquery));

        cq.multiselect(
                root.get(Post_.ID),
                root.get(Post_.USER_ID),
                root.get(Post_.TIME_POST),
                root.get(Post_.TEXT),
                root.get(Post_.LIKES),
                root.get(Post_.DIS_LIKES)
        );

        return entityManager.createQuery(cq)
                .setFirstResult((page.intValue() - 1) * 100)
                .setMaxResults(page.intValue() * 100)
                .getResultList();
    }

    @Transactional
    @Override
    public PostDto updatePost(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postUpdateDto.getId()).orElseThrow(() -> {throw new NotFoundException("Пост не был найден");
        });
        if(postUpdateDto.getText() != null){
            post.setText(postUpdateDto.getText());
        }
        if(postUpdateDto.getLikes() != null){
            post.setLikes(postUpdateDto.getLikes());
        }
        if(postUpdateDto.getDisLikes() != null){
            post.setDisLikes(postUpdateDto.getDisLikes());
        }
        return postMapper.postToPostDto(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        log.info("Удаление поста с id {}", id);
        postRepository.deleteById(id);
    }
}
