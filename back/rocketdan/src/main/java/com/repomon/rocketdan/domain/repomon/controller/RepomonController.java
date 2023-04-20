package com.repomon.rocketdan.domain.repomon.controller;

import com.repomon.rocketdan.domain.repo.dto.RepoResponseDto;
import com.repomon.rocketdan.domain.repo.dto.RepomonRequestDto;
import com.repomon.rocketdan.domain.repomon.dto.BattleLogResponseDto;
import com.repomon.rocketdan.domain.repomon.dto.RepomonStatusResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repomon")
public class RepomonController {

    @GetMapping("/{repoId}")
    public ResponseEntity<RepomonStatusResponseDto> getRepomonInfo(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{repoId}/match")
    public ResponseEntity<RepoResponseDto> getBattleTarget(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{repoId}/match")
    public ResponseEntity<BattleLogResponseDto> m1(){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{repoId}")
    public ResponseEntity<RepomonRequestDto> modifyRepomonStatus(Long repoId, RepomonRequestDto requestDto){
        return ResponseEntity.ok().build();
    }
}
