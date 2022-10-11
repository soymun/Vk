package com.example.vk.Facade;


import com.example.vk.DTO.authDto.AuthDTO;
import com.example.vk.DTO.authDto.RegDTO;
import com.example.vk.Entity.Role;
import com.example.vk.Entity.User;
import com.example.vk.Response.RegistrationResponse;
import com.example.vk.Security.JWTAuthException;
import com.example.vk.Security.JWTTokenProvider;
import com.example.vk.Service.Implaye.UserServiceImp;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AuthFacade {

    private final UserServiceImp userServiceImp;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public AuthFacade(UserServiceImp userServiceImp,
                      PasswordEncoder passwordEncoder,
                      AuthenticationManager authenticationManager,
                      JWTTokenProvider jwtTokenProvider) {
        this.userServiceImp = userServiceImp;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public RegistrationResponse registration(RegDTO regDTO){
        User user = userServiceImp.getUserByUsername(regDTO.getEmail());
        RegistrationResponse response = new RegistrationResponse();
        if(user != null){
            throw new NotFoundException("With this email, the user already exists");
        }
        else if(!regDTO.getPassword().equals(regDTO.getTwoPassword())){
            response.setError("Password don't equals");
            log.info("Password don't equals");
            return response;
        }
        User userAuth = new User();
        log.info("RegDTO mapped to user");
        userAuth.setEmail(regDTO.getEmail());
        userAuth.setPassword(passwordEncoder.encode(regDTO.getPassword()));
        userAuth.setName(regDTO.getName());
        userAuth.setSurname(regDTO.getSurname());
        userAuth.setRole(Role.USER);
        log.info("User created");
        userServiceImp.save(userAuth);
        response.setText("Registration suggest");
        return response;
    }

    public ResponseEntity<?> login(AuthDTO authDTO){
        try {
            if (authDTO == null || authDTO.getEmail() == null) {
                throw new NotFoundException("With this email, the user already exists");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
            log.info("User authenticated");
            User user = userServiceImp.getUserByUsername(authDTO.getEmail());
            String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());
            log.info("Token created");
            Map<Object, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", user.getEmail());
            response.put("token", token);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw new JWTAuthException("Token is already exists");
        }
    }
}
