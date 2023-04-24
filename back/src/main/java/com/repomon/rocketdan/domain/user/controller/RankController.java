package com.repomon.rocketdan.domain.user.controller;

import com.repomon.rocketdan.domain.repo.dto.RepoRankResponseDto;
import com.repomon.rocketdan.domain.repomon.dto.RepomonRankResponseDto;
import com.repomon.rocketdan.domain.user.dto.UserRankResponseDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rank")
public class RankController {
    @ApiOperation(value = "유저 랭킹 조회")
    @GetMapping("/user")
    public ResponseEntity<UserRankResponseDto> getUserRankList(){
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "레포 랭킹 조회")
    @GetMapping("/repo")
    public ResponseEntity<RepoRankResponseDto> getRepoRankList(){
        return ResponseEntity.ok().build();
    }
    @ApiOperation(value = "전투 랭킹 조회")
    @GetMapping("/repomon")
    public ResponseEntity<RepomonRankResponseDto> getRepomonRankList(){
        return ResponseEntity.ok().build();
    }
}
