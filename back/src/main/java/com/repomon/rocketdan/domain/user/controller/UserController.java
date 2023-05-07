package com.repomon.rocketdan.domain.user.controller;


import com.repomon.rocketdan.common.dto.ResultDto;
import com.repomon.rocketdan.domain.user.dto.RepresentRepomonRequestDto;
import com.repomon.rocketdan.domain.user.dto.UserCardResponseDto;
import com.repomon.rocketdan.domain.user.dto.UserResponseDto;
import com.repomon.rocketdan.domain.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserService userService;


	@ApiOperation("유저 정보를 조회합니다")
	@GetMapping("/{userId}")
	public ResponseEntity getUserInfo(@PathVariable("userId") Long userId) {

		UserResponseDto userResponseDto = userService.getUserInfo(userId);
		return ResponseEntity.ok().body(userResponseDto);
	}


	@ApiOperation(value = "대표 레포몬을 설정합니다.")
	@PutMapping("/{userId}/represent")
	public ResponseEntity<ResultDto<Boolean>> modifyRepresentRepo(@PathVariable("userId") Long userId,

		RepresentRepomonRequestDto requestDto) {
		userService.modifyRepresentRepo(userId, requestDto);
		return ResponseEntity.ok().body(ResultDto.ofSuccess());
	}


	@ApiOperation(value = "유저 카드 정보")
	@GetMapping("/{userId}/card")
	public ResponseEntity<UserCardResponseDto> getUserCard(@PathVariable("userId") Long userId) throws IOException, InterruptedException {
		UserCardResponseDto responseDto = userService.getUserCard(userId);
		return ResponseEntity.ok(responseDto);
	}

}
