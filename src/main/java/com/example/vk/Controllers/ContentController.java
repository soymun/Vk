package com.example.vk.Controllers;


import com.example.vk.DTO.follow.FromToUser;
import com.example.vk.DTO.profileDto.UserDTO;
import com.example.vk.Facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/user")
@CrossOrigin(origins="http://localhost:3000")
public class ContentController {


    private final UserFacade userFacade;

    @Autowired
    public ContentController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getProfile(@PathVariable("id") Long id){
        log.info("Get user with id: {}", id);
        return userFacade.getUser(id);
    }
    @PutMapping()
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> updateProfile(@RequestBody UserDTO userDTO){
        log.info("Update user with  and userDto: {}", userDTO);
        return userFacade.updateProfile(userDTO);
    }
    @GetMapping()
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getUsers(@RequestBody FromToUser fromToUser){
        log.info("Get user with radius");
        return userFacade.getUserInRadius(fromToUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        log.info("Delete user");
        userFacade.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
