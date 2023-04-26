package com.repomon.rocketdan.domain.repomon.controller;


import com.repomon.rocketdan.common.dto.ResultDto;
import com.repomon.rocketdan.domain.repomon.dto.*;
import com.repomon.rocketdan.domain.repomon.service.RepomonService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/repomon")
public class RepomonController {

	private final RepomonService repomonService;


	@PostMapping("/start")
	@ApiOperation(value = "레포몬의 초기 정보를 등록합니다.")
	public ResponseEntity<ResultDto<Boolean>> createRepomonStatus(
		RepomonStatusRequestDto repomonStatusRequestDto) {
		repomonService.createRepomonStatus(repomonStatusRequestDto);

		return ResponseEntity.ok().body(ResultDto.ofSuccess());
	}


	@GetMapping("/{repoId}")
	@ApiOperation(value = "레포몬의 정보를 조회합니다.")
	public ResponseEntity<ResultDto<RepomonStatusResponseDto>> getRepomonInfo(
		@PathVariable("repoId") Long repoId) {
		RepomonStatusResponseDto repomonStatusResponseDto = repomonService.getRepomonInfo(repoId);

		return ResponseEntity.ok().body(ResultDto.of(repomonStatusResponseDto));
	}


	@GetMapping("/{repoId}/match")
	@ApiOperation(value = "레포몬의 전투 상대를 매칭합니다.")
	public ResponseEntity<ResultDto<RepomonStatusResponseDto>> getBattleTarget(
		@PathVariable("repoId") Long repoId) {
		RepomonStatusResponseDto repomonStatusResponseDto = repomonService.getBattleTarget(repoId);
		return ResponseEntity.ok().body(ResultDto.of(repomonStatusResponseDto));
	}


	@PostMapping("/{repoId}/match")
	@ApiOperation(value = "레포몬의 전투 결과를 반환합니다.")
	public ResponseEntity<ResultDto<BattleLogResponseDto>> createBattleResult(
		@PathVariable("repoId") Long repoId,
		BattleLogRequestDto battleLogRequestDto) {

		return ResponseEntity.ok().body(ResultDto.of(new BattleLogResponseDto()));
	}


	@GetMapping("/{repoId}/match/result")
	@ApiOperation(value = "상위 5개의 전투기록을 조회합니다.")
	public ResponseEntity<ResultDto<BattleLogListResponseDto>> getBattleLogList(
		@PathVariable("repoId") Long repoId) {
		BattleLogListResponseDto battleLog = repomonService.getBattleLogList(repoId);

		return ResponseEntity.ok().body(ResultDto.of(battleLog));
	}


	@PutMapping("/stat")
	@ApiOperation(value = "레포몬의 스텟을 변경합니다.")
	public ResponseEntity<ResultDto<Boolean>> modifyRepomonStatus(
		RepomonStatusRequestDto repomonStatusRequestDto) {
		repomonService.modifyRepomonStatus(repomonStatusRequestDto);

		return ResponseEntity.ok().body(ResultDto.ofSuccess());
	}


	@PutMapping("/nickname")
	@ApiOperation(value = "레포몬의 닉네임을 변경합니다.")
	public ResponseEntity<ResultDto<Boolean>> modifyRepomonNickname(
		RepomonStatusRequestDto repomonStatusRequestDto) {
		repomonService.modifyRepomonNickname(repomonStatusRequestDto);

		return ResponseEntity.ok().body(ResultDto.ofSuccess());
	}

}
