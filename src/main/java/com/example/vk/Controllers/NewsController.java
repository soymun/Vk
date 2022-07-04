package com.example.vk.Controllers;


import com.example.vk.Entity.Post;
import com.example.vk.Service.Implaye.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private UserServiceImp userServiceImp;

    @GetMapping("/news/{id}")
    public List<Post> getNews(@PathVariable Long id, @RequestParam("skip") Long skip, @RequestParam("limit") Long limit){
        return userServiceImp.getNews(id, skip, limit);
    }
}
