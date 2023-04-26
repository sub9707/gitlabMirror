package com.repomon.rocketdan.domain.user.controller;

import com.repomon.rocketdan.common.dto.AuthResponseDto;
import com.repomon.rocketdan.domain.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/success")
    public ResponseEntity handleSuccessfulLogin(@RequestParam(value="access-token") String accessToken,
        @RequestParam(value="refresh-token") String refreshToken) {

        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add("accessToken", accessToken);
            httpHeaders.add("refreshToken", refreshToken);
        }).build();
    }


    /**
     * @param authHeader
     * @return
     */
    @GetMapping("/auth/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String authHeader) {

        String refreshToken = authHeader.replace("Bearer ", "");

        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }


    /**
     *
     * @param accessToken
     * @param refreshToken
     * @return
     */
    @PutMapping("/refresh")
    public ResponseEntity reissueJWT(@RequestHeader("accessToken") String accessToken, @RequestHeader("refreshToken") String refreshToken){

        AuthResponseDto authResponseDto = authService.refresh(accessToken, refreshToken);

        return ResponseEntity.ok().headers(httpHeaders -> {
            httpHeaders.add("accessToken", authResponseDto.getAccessToken());
            httpHeaders.add("refreshToken", authResponseDto.getRefreshToken());
        }).build();
    }
}
