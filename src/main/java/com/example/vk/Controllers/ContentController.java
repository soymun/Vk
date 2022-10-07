package com.example.vk.Controllers;


import com.example.vk.DTO.FromToUser;
import com.example.vk.DTO.UserDTO;
import com.example.vk.Facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/vk")
@CrossOrigin(origins="http://localhost:3000")
public class ContentController {


    private final UserFacade userFacade;

    @Autowired
    public ContentController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getProfile(@PathVariable("id") Long id){
        log.info("");
        return ResponseEntity.ok(userFacade.getUser(id));
    }
    @PutMapping("/profile/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable("id") Long id,@RequestBody UserDTO userDTO){
        log.info("");
        return ResponseEntity.ok(userFacade.updateProfile(id, userDTO));
    }
    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestBody FromToUser fromToUser){
        log.info("");
        List<UserDTO> userDTOS = userFacade.getUserInRadius(fromToUser.getFrom(), fromToUser.getTo());
        return ResponseEntity.ok(userDTOS);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        log.info("");
        userFacade.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
