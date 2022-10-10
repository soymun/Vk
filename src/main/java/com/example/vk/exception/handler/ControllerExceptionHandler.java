package com.example.vk.exception.handler;


import com.example.vk.Response.RegistrationResponse;
import com.example.vk.exception.NotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;


@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundExceptionException(@NonNull final NotFoundException exc) {
        RegistrationResponse response = new RegistrationResponse();
        log.error(exc.getMessage());
        response.setError(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({NoResultException.class})
    ResponseEntity<?> handleNoResultException(@NonNull NoResultException exc) {
        RegistrationResponse response = new RegistrationResponse();
        log.error(exc.getMessage());
        response.setError(exc.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
