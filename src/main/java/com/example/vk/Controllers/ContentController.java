package com.example.vk.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vk")
public class ContentController {

    @GetMapping("/message")
    public String hello(){
        System.out.println("1");
        return "1";
    }
}
