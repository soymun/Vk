package com.example.vk.Controllers;

import com.example.vk.DTO.AuthDTO;
import com.example.vk.DTO.RegDTO;
import com.example.vk.Exeption.NotFoundException;
import com.example.vk.Facade.AuthFacade;
import com.example.vk.Response.RegistrationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/vk")
@CrossOrigin(origins="http://localhost:3000")
public class AuthControllers {

    private final AuthFacade authenticate;

    @Autowired
    public AuthControllers(AuthFacade authenticate) {
        this.authenticate = authenticate;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegDTO regDTO){
        if(regDTO == null){
            throw new NotFoundException("With this email, the user already exists");
        }
        log.info("Registration user");
        RegistrationResponse response = authenticate.registration(regDTO);
        log.info(String.format("Suggest registration user with email:%s, name:%s, surname:%s", regDTO.getEmail(), regDTO.getName(), regDTO.getSurname()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthDTO authDTO){
        log.info(String.format("Login user with email:%s", authDTO.getEmail()));
        return authenticate.login(authDTO);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        log.info("Logout user");
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
