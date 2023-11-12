package com.example.controller;

import com.example.entity.UsersEntity;
import com.example.dto.TokenResponse;
import com.example.dto.UserRequest;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;
    @PostMapping("/user/signUp")
    public ResponseEntity signUp(@RequestBody UserRequest userRequest) {
        return userService.findByUserId(userRequest.getUserId()).isPresent()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(userService.signUp(userRequest));
    }
    @PostMapping("/user/signIn")
    public ResponseEntity<TokenResponse> signIn(@RequestBody UserRequest userRequest) {
        System.out.println(userRequest.getUserId());
        System.out.println(userRequest.getUserPw());
        TokenResponse tokenResponse = userService.signIn(userRequest);
        System.out.println(tokenResponse.getMemberInfo().getNickName());
        System.out.println(tokenResponse.getMemberInfo().getUserId());
        return ResponseEntity.ok().body(tokenResponse);
    }
}
