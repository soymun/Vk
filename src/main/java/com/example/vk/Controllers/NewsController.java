package com.example.vk.Controllers;


import com.example.vk.Facade.UserFacade;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getNews(@PathVariable Long id, @RequestParam("page") Long page){
        if (id == null){
            throw new NotFoundException("Don't found news");
        }
        log.info("Get news");
        return userFacade.getNews(id, page);
    }

    @PatchMapping("/post/like/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> addLike(@PathVariable("id") Long id){
        if(id == null){
            throw new NotFoundException("Don't found post");
        }
        log.info("Add like");
        return userFacade.addLike(id);
    }

    @PatchMapping("/post/dislike/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> addDislike(@PathVariable("id") Long id){
        if(id == null){
            throw new NotFoundException("Don't found post");
        }
        log.info("Add dislike");
        return userFacade.addDislikes(id);
    }
}
