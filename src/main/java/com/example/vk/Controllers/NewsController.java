package com.example.vk.Controllers;


import com.example.vk.DTO.newsDto.News;
import com.example.vk.Facade.UserFacade;
import com.example.vk.Response.PostDtoResponse;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<News> getNews(@PathVariable Long id, @RequestParam("skip") Long skip, @RequestParam("limit") Long limit){
        if (id == null){
            throw new NotFoundException("Don't found news");
        }
        log.info("Get news");
        return userFacade.getNews(id, skip, limit);
    }

    @PostMapping("/post/{id}")
    public PostDtoResponse addLike(@PathVariable("id") Long id){
        if(id == null){
            throw new NotFoundException("Don't found post");
        }
        log.info("Add like");
        return userFacade.addLike(id);
    }
}
