package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<?> handleUserIdNotFoundException(UserIdNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", Map.of("title", "아이디를 잘못 입력하셨어요!", "description", "아이디를 다시 한 번 확인해주세요.")));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<?> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", Map.of("title", "패스워드를 잘못 입력하셨어요!", "description", "패스워드를 다시 한 번 확인해주세요.")));
    }
}
