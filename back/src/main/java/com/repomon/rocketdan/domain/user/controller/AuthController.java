package com.repomon.rocketdan.domain.user.controller;

import com.repomon.rocketdan.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    @ResponseBody
    public ResponseEntity signin(@RequestParam("code") String code) {

        authService.signin(code);
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
