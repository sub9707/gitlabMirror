package com.repomon.rocketdan.domain.repo.service;

import com.repomon.rocketdan.common.GHUtils;
import com.repomon.rocketdan.domain.repo.app.RepoDetail;
import com.repomon.rocketdan.domain.repo.dto.response.RepoBattleResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoContributeResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoConventionResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoListResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoResearchResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoResponseDto;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import com.repomon.rocketdan.domain.repo.repository.ActiveRepoRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoHistoryRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import com.repomon.rocketdan.domain.repo.repository.RepomonRepository;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import com.repomon.rocketdan.domain.repomon.repository.RepomonStatusRepository;
import com.repomon.rocketdan.domain.user.entity.ActiveRepoEntity;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import com.repomon.rocketdan.exception.CustomException;
import com.repomon.rocketdan.exception.ErrorCode;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.PagedIterable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@Transactional
@RequiredArgsConstructor
public class RepoService {

    private final GHUtils ghUtils;
    private final UserRepository userRepository;
    private final RepoRepository repoRepository;
    private final RepomonRepository repomonRepository;
    private final RepomonStatusRepository repomonStatusRepository;
    private final RepoHistoryRepository repoHistoryRepository;
    private final ActiveRepoRepository activeRepoRepository;

    /**
     * 레포 전체 조회
     * @param userId
     * @param pageable
     * @return
     */
    public RepoListResponseDto getUserRepoList(Long userId, Pageable pageable){
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() ->{
            throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
        });

        String userName = userEntity.getUserName();
        Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(userName);

        saveAndUpdateRepo(repositories, userEntity);


        Page<ActiveRepoEntity> activeRepoPage = activeRepoRepository.findByUser(userEntity, pageable);
        List<RepoDetail> repoDetails = activeRepoPage.stream()
            .map(activeRepoEntity -> {
                RepoEntity repoEntity = activeRepoEntity.getRepo();
                GHRepository ghRepository = repositories.get(repoEntity.getRepoKey());
                return convertActiveRepoToRepo(activeRepoEntity, ghRepository);
            }).collect(Collectors.toList());

        long totalElements = activeRepoPage.getTotalElements();
        int totalPages = activeRepoPage.getTotalPages();
        return RepoListResponseDto.fromDetails(repoDetails, totalElements, totalPages);
    }

    /**
     * 레포 상세 조회
     * 레포 이름, 스타, 포크 수
     * 레포몬 이름, 레포 시작날짜, 종료날짜
     * 경험치, 랭킹, 성장요소 ( 히스토리 분석 ), 히스토리
     * @param repoId
     * @return
     */
    public RepoResponseDto getUserRepoInfo(Long repoId) {
        RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
        });

        String repoOwner = repoEntity.getRepoOwner();

        String repoKey = repoEntity.getRepoKey();
        Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(repoOwner);
        GHRepository ghRepository = repositories.get(repoKey);

        return RepoResponseDto.fromEntityAndGHRepository(repoEntity, ghRepository);
    }

    /**
     * 레포 분석 조회
     * 경험치, 랭킹
     * 커밋, 머지, 이슈, 리뷰, 보안성, 효율성
     * 레포 히스토리 데이터 내려주기
     * @param repoId
     * @return
     */
    public RepoResearchResponseDto getRepoResearchInfo(Long repoId){
        RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
        });

        // TODO 랭킹 순위 받기
        int rank = 0;

        // TODO repo history 범위 설정
        List<RepoHistoryEntity> historyEntityList = repoHistoryRepository.findAllByRepo(repoEntity);

        return RepoResearchResponseDto.fromHistoryAndRank(historyEntityList, rank, repoEntity.getRepoExp());
    }

    /**
     * 레포 배틀 조회
     * 레이팅, 랭킹, 승, 패
     * 능력치 내려주기
     * @param repoId
     * @return
     */

    public RepoBattleResponseDto getRepoBattleInfo(Long repoId){
        RepomonStatusEntity repomonStatusEntity = repomonStatusRepository.findByRepoId(repoId)
            .orElseThrow(() -> {
                throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
            });

        int rank = 0;

        return RepoBattleResponseDto.fromStatusEntity(repomonStatusEntity, rank);
    }

    /**
     * 레포 컨벤션 조회
     * 현재 등록된 컨벤션 목록
     * 컨벤션 준수율 내려주기
     * @param repoId
     * @return
     */
    public RepoConventionResponseDto getRepoConventionInfo(Long repoId){
        return null;
    }

    /**
     * 레포 기여도 조회
     * 작성한 코드 수 or 커밋 수로 통계 내려주기
     * @param repoId
     * @return
     */
    public RepoContributeResponseDto getRepoContributeInfo(Long repoId){
        return null;
    }


    /**
     * 신규 레포 등록이라면 상세분석해서 전부 저장해야함
     * @param repositories
     * @param userEntity
     */
    private void saveAndUpdateRepo(Map<String, GHRepository> repositories, UserEntity userEntity) {
        repositories.forEach((s, ghRepository) -> {
            repoRepository.findByRepoKey(s).ifPresentOrElse(repoEntity -> repoEntity.update(ghRepository),
                () -> {
                    RepomonEntity repomonEntity = repomonRepository.findById(9999L).orElseThrow(()->{
                        throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
                    });


                    RepomonStatusEntity repomonStatusEntity = RepomonStatusEntity.fromGHRepository(ghRepository,
                        repomonEntity);
                    repomonStatusRepository.save(repomonStatusEntity);
                    activeRepoRepository.save(ActiveRepoEntity.of(userEntity, repomonStatusEntity));

                    initRepositoryInfo(repomonStatusEntity, ghRepository);
                });
        });
    }

    private void initRepositoryInfo(RepoEntity repoEntity, GHRepository ghRepository) {
        log.info("최초 등록 레포지토리 분석 시작 => {}", repoEntity.getRepoName());
        try {
            List<RepoHistoryEntity> list = new ArrayList<>();
            list.addAll(ghUtils.GHCommitToHistory(ghRepository, repoEntity));
            list.addAll(ghUtils.GHPullRequestToHistory(ghRepository, repoEntity));
            list.addAll(ghUtils.GHIssueToHistory(ghRepository, repoEntity));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("최초 등록 레포지토리 분석 종료 => {}", repoEntity.getRepoName());
    }

    private RepoDetail convertActiveRepoToRepo(ActiveRepoEntity activeRepoEntity, GHRepository ghRepository){
        RepoEntity repoEntity = activeRepoEntity.getRepo();
        return new RepoDetail(repoEntity
            , ghRepository == null ? "비공개 처리된 레포지토리입니다." : ghRepository.getDescription()
            , activeRepoEntity.getIsActive()
            , ghRepository == null);
    }
}
