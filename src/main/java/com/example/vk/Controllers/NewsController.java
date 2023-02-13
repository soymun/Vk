package com.example.vk.Controllers;


import com.example.vk.DTO.newsDto.News;
import com.example.vk.Facade.UserFacade;
import com.example.vk.Response.PostDtoResponse;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/vk")
@Slf4j
public class NewsController {

    private final UserFacade userFacade;

    @Autowired
    public NewsController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/news/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public List<News> getNews(@PathVariable Long id, @RequestParam("page") Long page){
        if (id == null){
            throw new NotFoundException("Don't found news");
        }
        log.info("Get news");
        return userFacade.getNews(id, page);
    }

    @PostMapping("/post/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public PostDtoResponse addLike(@PathVariable("id") Long id){
        if(id == null){
            throw new NotFoundException("Don't found post");
        }
        log.info("Add like");
        return userFacade.addLike(id);
    }
}
