package com.repomon.rocketdan.domain.repomon.controller;

import com.repomon.rocketdan.common.dto.ResultDto;
import com.repomon.rocketdan.domain.repo.dto.RepoResponseDto;
import com.repomon.rocketdan.domain.repo.dto.RepomonRequestDto;
import com.repomon.rocketdan.domain.repo.dto.RepomonResponseDto;
import com.repomon.rocketdan.domain.repomon.dto.BattleLogRequestDto;
import com.repomon.rocketdan.domain.repomon.dto.BattleLogResponseDto;
import com.repomon.rocketdan.domain.repomon.dto.RepomonStatusResponseDto;
import com.repomon.rocketdan.domain.repomon.service.RepomonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repomon")
public class RepomonController {

    private final RepomonService repomonService;

    @GetMapping("/{repoId}")
    public ResponseEntity<ResultDto<RepomonStatusResponseDto>> getRepomonInfo(@PathVariable("repoId") Long repoId){
        RepomonStatusResponseDto repomonStatusResponseDto = repomonService.getRepomonInfo(repoId);

        return ResponseEntity.ok().body(ResultDto.of(repomonStatusResponseDto));
    }

    @GetMapping("/{repoId}/match")
    public ResponseEntity<ResultDto<RepoResponseDto>> getBattleTarget(@PathVariable("repoId") Long repoId){

        return ResponseEntity.ok().body(ResultDto.of(new RepoResponseDto()));
    }

    @PostMapping("/{repoId}/match")
    public ResponseEntity<ResultDto<BattleLogResponseDto>> createBattleResult(@PathVariable("repoId") Long repoId,
                                                                              BattleLogRequestDto battleLogRequestDto){

        return ResponseEntity.ok().body(ResultDto.of(new BattleLogResponseDto()));
    }

    @GetMapping("/{repoId}/match/result")
    public ResponseEntity<ResultDto<BattleLogResponseDto>> getBattleLogList(@PathVariable("repoId") Long repoId){


        return ResponseEntity.ok().body(ResultDto.of(new BattleLogResponseDto()));
    }

    @PutMapping("/{repoId}")
    public ResponseEntity<ResultDto<RepomonResponseDto>> modifyRepomonStatus(@PathVariable("repoId") Long repoId,
                                                                 RepomonRequestDto repomonRequestDto){

        return ResponseEntity.ok().body(ResultDto.of(new RepomonResponseDto()));
    }
}
