package com.mercadolibre.be_java_hisp_w23_g2.exception;

import com.mercadolibre.be_java_hisp_w23_g2.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler
    public ResponseEntity<?> notFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> badRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<?> NotFollowingException(NotFollowingException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDTO(e.getMessage()));
    }
}
