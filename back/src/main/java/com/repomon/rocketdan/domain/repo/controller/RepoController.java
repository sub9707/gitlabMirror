package com.repomon.rocketdan.domain.repo.controller;

import com.repomon.rocketdan.common.dto.ResultDto;
import com.repomon.rocketdan.domain.repo.dto.request.RepoCardRequestDto;
import com.repomon.rocketdan.domain.repo.dto.request.RepoConventionRequestDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoBattleResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoCardResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoContributeResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoConventionResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoListResponseDto;
import com.repomon.rocketdan.domain.repo.dto.request.RepoPeriodRequestDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoResearchResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoResponseDto;
import com.repomon.rocketdan.domain.repo.service.RepoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/repo")
@RequiredArgsConstructor
public class RepoController {

    private final RepoService repoService;

    @ApiOperation(value = "전체 레포 리스트 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<RepoListResponseDto> getUserRepoList(@PathVariable Long userId, @PageableDefault(size = 6, direction = Direction.DESC) Pageable pageable){
        RepoListResponseDto responseDto = repoService.getUserRepoList(userId, pageable);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "개별 레포 디테일 조회")
    @GetMapping("/{repoId}/info")
    public ResponseEntity<RepoResponseDto> getRepoInfo(@PathVariable Long repoId){
        RepoResponseDto responseDto = repoService.getUserRepoInfo(repoId);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * TODO 카테고리별로 총 API 4개 추가해야함
     * @param repoId
     * @return
     */
    @ApiOperation(value = "개별 레포 분석 조회")
    @GetMapping("/{repoId}/info/research")
    public ResponseEntity<RepoResearchResponseDto> getRepoResearchInfo(@PathVariable Long repoId){
        RepoResearchResponseDto responseDto = repoService.getRepoResearchInfo(repoId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "개별 레포 랭킹정보 조회")
    @GetMapping("/{repoId}/info/rank")
    public ResponseEntity<RepoBattleResponseDto> getRepoBattleInfo(@PathVariable Long repoId){
        RepoBattleResponseDto responseDto = repoService.getRepoBattleInfo(repoId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "개별 레포 컨벤션 정보 조회")
    @GetMapping("/{repoId}/info/convention")
    public ResponseEntity<RepoConventionResponseDto> getRepoConventionInfo(@PathVariable Long repoId){
        RepoConventionResponseDto responseDto = repoService.getRepoConventionInfo(repoId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "개별 레포 기여도 조회")
    @GetMapping("/{repoId}/info/contribute")
    public ResponseEntity<RepoContributeResponseDto> getRepoContributeInfo(@PathVariable Long repoId){
        RepoContributeResponseDto responseDto = repoService.getRepoContributeInfo(repoId);
        return ResponseEntity.ok(responseDto);
    }

    @ApiOperation(value = "개별 레포 정보 갱신")
    @PutMapping("/{repoId}/info")
    public ResponseEntity<ResultDto> modifyRepoInfo(@PathVariable Long repoId){
        repoService.modifyRepoInfo(repoId);
        return ResponseEntity.ok(ResultDto.ofSuccess());
    }


    @ApiOperation(value = "레포지토리 기간 설정")
    @PutMapping("/{repoId}/info/period")
    public ResponseEntity modifyRepoPeriod(@PathVariable Long repoId, @RequestBody RepoPeriodRequestDto requestDto){
        repoService.modifyRepoPeriod(repoId, requestDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "레포지토리 컨벤션 설정")
    @PutMapping("/{repoId}/info/convention")
    public ResponseEntity<ResultDto> modifyRepoConvention(@PathVariable Long repoId, @ModelAttribute RepoConventionRequestDto requestDto){
        repoService.modifyRepoConvention(repoId, requestDto);
        return ResponseEntity.ok(ResultDto.ofSuccess());
    }

    @ApiOperation(value = "레포지토리 활성화 설정")
    @PutMapping("/{repoId}/info/active")
    public ResponseEntity<ResultDto> modifyRepoActive(@PathVariable Long repoId){
        repoService.activateRepo(repoId);
        return ResponseEntity.ok(ResultDto.ofSuccess());
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
