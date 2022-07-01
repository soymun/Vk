package com.example.vk.Controllers;

import com.example.vk.DTO.AuthDTO;
import com.example.vk.DTO.RegDTO;
import com.example.vk.Entity.Role;
import com.example.vk.Entity.User;
import com.example.vk.Service.Implaye.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/vk")
public class AuthControllers {


    private final UserServiceImp userServiceImp;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthControllers(UserServiceImp userServiceImp, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userServiceImp = userServiceImp;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> getUsers(@RequestBody RegDTO regDTO){
        User user = userServiceImp.getUserByUsername(regDTO.getEmail());
        if(user != null){
            throw new RuntimeException("With this email, the user already exists");
        }
        else if(!regDTO.getPassword().equals(regDTO.getTwoPassword())){
            throw new RuntimeException("Password don't equals");
        }
        User userAuth = new User();
        userAuth.setEmail(regDTO.getEmail());
        userAuth.setPassword(passwordEncoder.encode(regDTO.getPassword()));
        userAuth.setName(regDTO.getName());
        userAuth.setSurname(regDTO.getSurname());
        userAuth.setRoles(List.of(Role.USER));
        userServiceImp.save(userAuth);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody AuthDTO authDTO){
        if(authDTO == null){
            throw new RuntimeException("Null email and password");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
