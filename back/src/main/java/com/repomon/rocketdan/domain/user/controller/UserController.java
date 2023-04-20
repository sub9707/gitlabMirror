package com.repomon.rocketdan.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/{userId}")
    public ResponseEntity getUserInfo(Long userId){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity modifyUserInfo(Long userId){
        return ResponseEntity.ok().build();
    }
}
