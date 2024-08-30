package com.brunodias.dbooking.application.exceptions;

import com.brunodias.dbooking.application.dtos.CustomErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomErrorDTO> resourceNotFoundException(UserAlreadyExistsException e,
                                                                    HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomErrorDTO err = new CustomErrorDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
