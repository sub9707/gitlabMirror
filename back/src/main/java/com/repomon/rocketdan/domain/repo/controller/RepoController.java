package com.repomon.rocketdan.domain.repo.controller;

import com.repomon.rocketdan.domain.repo.dto.RepoCardRequestDto;
import com.repomon.rocketdan.domain.repo.dto.RepoCardResponseDto;
import com.repomon.rocketdan.domain.repo.dto.RepoListResponseDto;
import com.repomon.rocketdan.domain.repo.dto.RepoRequestDto;
import com.repomon.rocketdan.domain.repo.dto.RepoResponseDto;
import com.repomon.rocketdan.domain.user.dto.ActiveRepoRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/repo")
public class RepoController {

    @ApiOperation(value = "전체 레포 리스트 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<RepoListResponseDto> getUserRepoList(Long userId){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "개별 레포 디테일 조회")
    @GetMapping("/{repoId}/info")
    public ResponseEntity<RepoResponseDto> getUserRepoInfo(Long repoId){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "개별 레포 정보 갱신")
    @PutMapping("/{repoId}/info")
    public ResponseEntity<RepoResponseDto> modifyRepoInfo(Long repoId){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "레포몬 닉네임 수정")
    @PutMapping("/{repoId}/info/nickname")
    public ResponseEntity modifyRepomonNickname(Long repoId, RepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "레포지토리 기간 설정")
    @PutMapping("/{repoId}/info/period")
    public ResponseEntity modifyRepoPeriod(Long repoId, RepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "레포지토리 컨벤션 설정")
    @PutMapping("/{repoId}/info/convention")
    public ResponseEntity modifyRepoConvention(Long repoId, RepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "레포지토리 활성화 설정")
    @PutMapping("/{repoId}/info/active")
    public ResponseEntity modifyRepoActive(Long repoId, ActiveRepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "대표레포카드 조회 및 발급")
    @GetMapping("/{repoId}/card")
    public ResponseEntity<RepoCardResponseDto> getRepoCard(Long repoId){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "개인레포카드 조회")
    @GetMapping("/{repoId}/card/personal")
    public ResponseEntity<RepoCardResponseDto> getPersonalRepoCard(Long repoId){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "개인레포카드 수정")
    @PutMapping("/{repoId}/card/personal")
    public ResponseEntity modifyPersonalRepoCard(Long repoId, RepoCardRequestDto requestDto){
        return ResponseEntity.ok().build();
    }
}
