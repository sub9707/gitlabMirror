package com.repomon.rocketdan.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/signin")
    public ResponseEntity login(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity logout(){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refresh")
    public ResponseEntity reissueJWT(@RequestHeader String accessToken, @RequestHeader String refreshToken){
        return ResponseEntity.ok().build();
    }
}
