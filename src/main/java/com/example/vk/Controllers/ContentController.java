package com.example.vk.Controllers;


import com.example.vk.Controllers.Funchional.UserF;
import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.User;
import com.example.vk.Service.Implaye.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vk")
public class ContentController {


    private final UserServiceImp userServiceImp;
    @Autowired
    public ContentController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") Long id){
        if(id == null){
            throw new RuntimeException("Id is null");
        }
        User user = userServiceImp.getUserById(id);
        if(user == null){
               return new ResponseEntity<>(HttpStatus.PERMANENT_REDIRECT);
        }
        return ResponseEntity.ok(new UserDTO(user));
    }
    @PostMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") Long id,@RequestBody UserDTO userDTO){
        if(id == null){
            throw new RuntimeException("Id is null");
        }
        User user = userServiceImp.getUserById(id);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.PERMANENT_REDIRECT);
        }
        userServiceImp.save(UserF.save(user, userDTO));
        return ResponseEntity.ok(userDTO);
    }
    @GetMapping("users")
    public ResponseEntity<?> getUsers(){
        List<UserDTO> userDTOS = new ArrayList<>();
        userServiceImp.findAll().forEach(n -> userDTOS.add(new UserDTO(n)));
        return ResponseEntity.ok(userDTOS);
    }
}
