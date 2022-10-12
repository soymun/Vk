package com.example.vk.Controllers;


import com.example.vk.DTO.PostDto;
import com.example.vk.DTO.follow.FromToUser;
import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.Facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getProfile(@PathVariable("id") Long id){
        log.info("Get user with id: {}", id);
        return ResponseEntity.ok(userFacade.getUser(id));
    }
    @PutMapping("/profile/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> updateProfile(@PathVariable("id") Long id,@RequestBody UserDTO userDTO){
        log.info("Update user with id:{} and userDto: {}", id, userDTO);
        return ResponseEntity.ok(userFacade.updateProfile(id, userDTO));
    }
    @GetMapping("/users")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getUsers(@RequestBody FromToUser fromToUser){
        log.info("Get user with radius");
        List<UserDTO> userDTOS = userFacade.getUserInRadius(fromToUser.getUserId(), fromToUser.getFrom(), fromToUser.getTo());
        return ResponseEntity.ok(userDTOS);
    }

    @DeleteMapping("/user/delete/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        log.info("Delete user");
        userFacade.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/user/post")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto){
        return ResponseEntity.ok(userFacade.createPost(postDto));
    }
}
