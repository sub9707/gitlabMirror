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

@RestController
@RequestMapping("/repo")
public class RepoController {


    @GetMapping("/{userId}")
    public ResponseEntity<RepoListResponseDto> getUserRepoList(Long userId){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{repoId}/info")
    public ResponseEntity<RepoResponseDto> getUserRepoInfo(Long repoId){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{repoId}/info")
    public ResponseEntity<RepoResponseDto> modifyRepoInfo(Long repoId){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{repoId}/info/nickname")
    public ResponseEntity modifyRepomonNickname(Long repoId, RepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{repoId}/info/period")
    public ResponseEntity modifyRepoPeriod(Long repoId, RepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{repoId}/info/convention")
    public ResponseEntity modifyRepoConvention(Long repoId, RepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{repoId}/info/active")
    public ResponseEntity modifyRepoActive(Long repoId, ActiveRepoRequestDto requestDto){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{repoId}/card")
    public ResponseEntity<RepoCardResponseDto> getRepoCard(Long repoId){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{repoId}/card/personal")
    public ResponseEntity<RepoCardResponseDto> getPersonalRepoCard(Long repoId){
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{repoId}/card/personal")
    public ResponseEntity modifyPersonalRepoCard(Long repoId, RepoCardRequestDto requestDto){
        return ResponseEntity.ok().build();
    }
}
