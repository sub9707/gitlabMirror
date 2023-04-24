package com.repomon.rocketdan.domain.user.controller;

import com.repomon.rocketdan.common.dto.AuthResponseDto;
import com.repomon.rocketdan.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/success")
    public ResponseEntity handleSuccessfulLogin(@RequestParam(value="access-token") String accessToken,
        @RequestParam(value="refresh-token") String refreshToken) {

        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);

        AuthResponseDto authResponseDto = new AuthResponseDto(accessToken, refreshToken);
        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add("accessToken", accessToken);
            httpHeaders.add("refreshToken", refreshToken);
        }).build();
    }

    @GetMapping("/auth/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String authHeader) {

        String accessToken = authHeader.replace("Bearer ", "");
        System.out.println("accessToken = " + accessToken);

        authService.logout(accessToken);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refresh")
    public ResponseEntity reissueJWT(@RequestHeader("Authorization") String authHeader){

        String accessToken = authHeader.replace("Bearer ", "");
        System.out.println("accessToken = " + accessToken);

        authService.refresh(accessToken);
        return ResponseEntity.ok().build();
    }
}
