package com.example.vk.Controllers;

import com.example.vk.DTO.authDto.AuthDTO;
import com.example.vk.DTO.authDto.ReLoginDto;
import com.example.vk.DTO.authDto.RegDTO;
import com.example.vk.Facade.AuthFacade;
import com.example.vk.Response.RegistrationResponse;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/v1")
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

    @PostMapping("/relogin")
    public ResponseEntity<?> reLogin(@RequestBody ReLoginDto reLoginDto){
        log.info("Пере аунтефикация пользователя с id {}", reLoginDto.getUserId());
        return authenticate.reLogin(reLoginDto);
    }
}
