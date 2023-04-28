package com.repomon.rocketdan.domain.repo.service;

import com.repomon.rocketdan.common.GHUtils;
import com.repomon.rocketdan.domain.repo.app.RepoDetail;
import com.repomon.rocketdan.domain.repo.dto.response.RepoBattleResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoContributeResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoConventionResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoListResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoResearchResponseDto;
import com.repomon.rocketdan.domain.repo.dto.response.RepoResponseDto;
import com.repomon.rocketdan.domain.repo.entity.RepoConventionEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import com.repomon.rocketdan.domain.repo.repository.ActiveRepoRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoConventionRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoHistoryRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import com.repomon.rocketdan.domain.repo.repository.RepomonRepository;
import com.repomon.rocketdan.domain.repo.repository.redis.RepoRedisContributeRepository;
import com.repomon.rocketdan.domain.repo.repository.redis.RepoRedisConventionRepository;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import com.repomon.rocketdan.domain.repomon.repository.RepomonStatusRepository;
import com.repomon.rocketdan.domain.user.entity.ActiveRepoEntity;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import com.repomon.rocketdan.exception.CustomException;
import com.repomon.rocketdan.exception.ErrorCode;
import java.io.IOException;
import java.time.Instant;
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
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepository.Contributor;
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
    private final RepoConventionRepository conventionRepository;
    private final ActiveRepoRepository activeRepoRepository;

    // redis repository
    private final RepoRedisContributeRepository redisContributeRepository;
    private final RepoRedisConventionRepository redisConventionRepository;

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
                return ActiveRepoEntity.convertToRepo(activeRepoEntity, ghRepository);
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

        // TODO 랭킹 순위 받기
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
        RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
        });
        String repoOwner = repoEntity.getRepoOwner();

        RepoConventionResponseDto responseDto = redisConventionRepository.findByRepoOwner(
                repoOwner)
            .orElseGet(() -> findConvetionDtoWithGHApi(repoEntity, repoOwner));

        return responseDto;
    }

    /**
     * 레포 기여도 조회
     * 작성한 코드 수 or 커밋 수로 통계 내려주기
     * @param repoId
     * @return
     */
    public RepoContributeResponseDto getRepoContributeInfo(Long repoId){
        RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
        });

        String repoOwner = repoEntity.getRepoOwner();

        RepoContributeResponseDto responseDto = redisContributeRepository.findByRepoOwner(repoOwner)
            .orElseGet(() -> findContributeDtoWithGHApi(repoEntity, repoOwner));

        return responseDto;
    }

    /**
     * 레포 정보 갱신
     * @param repoId
     */
    public void modifyRepoInfo(Long repoId) {
        RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
        });

        String repoOwner = repoEntity.getRepoOwner();
        Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(repoOwner);

        String repoKey = repoEntity.getRepoKey();
        GHRepository ghRepository = repositories.get(repoKey);

        updateRepositoryInfo(repoEntity, ghRepository);
    }

    /**
     * 레포 활성화
     * @param repoId
     */
    public void activateRepo(Long repoId) {
        RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
            throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
        });

        if(repoEntity.getIsActive()){
            repoEntity.deActivate();
        }else{
            repoEntity.activate();
        }
    }

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

                    try {
                        Date createdAt = ghRepository.getCreatedAt();
                        initRepositoryInfo(repomonStatusEntity, ghRepository, createdAt);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        });
    }


    private RepoContributeResponseDto findContributeDtoWithGHApi(RepoEntity repoEntity, String repoOwner){
        Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(repoOwner);

        String repoKey = repoEntity.getRepoKey();
        GHRepository ghRepository = repositories.get(repoKey);
        try {
            int totalCommitCount = ghRepository
                .queryCommits().list()
                .toList().size();

            Map<String, Integer> commitCountMap = new HashMap<>();
            PagedIterable<Contributor> contributors = ghRepository.listContributors();
            for(Contributor contributor : contributors){
                String author = contributor.getLogin();
                int authorCommitCnt = ghRepository.queryCommits()
                    .author(author).list()
                    .toList().size();

                commitCountMap.put(author, authorCommitCnt);
            }

            String mvp = null;
            for (String user : commitCountMap.keySet()) {
                if(mvp == null || commitCountMap.get(user) > commitCountMap.get(mvp)){
                    mvp = user;
                }
            }

            RepoContributeResponseDto responseDto = RepoContributeResponseDto.of(totalCommitCount, commitCountMap, mvp, repoOwner);

            redisContributeRepository.save(responseDto);
            return responseDto;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private RepoConventionResponseDto findConvetionDtoWithGHApi(RepoEntity repoEntity, String repoOwner){
        List<RepoConventionEntity> conventions = conventionRepository.findAllByRepo(repoEntity);

        int totalCnt = 0;
        int collectCnt = 0;
        if(!conventions.isEmpty()) {
            log.info("컨벤션 분석 시작");

            Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(
                repoOwner);

            GHRepository ghRepository = repositories.get(repoEntity.getRepoKey());
            try {
                List<GHCommit> ghCommits = ghRepository.listCommits().toList();
                for(GHCommit commit : ghCommits){
                    totalCnt++;
                    String message = commit.getCommitShortInfo().getMessage();

                    boolean present = conventions.stream().anyMatch(convention -> {
                        String prefix = convention.getRepoConventionType();
                        return message.startsWith(prefix);
                    });

                    if(present){
                        collectCnt++;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            log.info("컨벤션 분석 끝");
        }

        RepoConventionResponseDto responseDto = RepoConventionResponseDto.fronEntities(repoOwner, conventions, totalCnt, collectCnt);
        redisConventionRepository.save(responseDto);
        return responseDto;
    }
    private void initRepositoryInfo(RepoEntity repoEntity, GHRepository ghRepository, Date fromDate) {
        log.info("레포지토리 분석 시작 => {}", repoEntity.getRepoName());
        try {
            List<RepoHistoryEntity> list = new ArrayList<>();
            list.addAll(ghUtils.GHCommitToHistory(ghRepository, repoEntity, fromDate));
            list.addAll(ghUtils.GHPullRequestToHistory(ghRepository, repoEntity, fromDate));
            list.addAll(ghUtils.GHIssueToHistory(ghRepository, repoEntity, fromDate));
            // 리뷰??

            Long totalExp = 0L;
            for(RepoHistoryEntity item : list){
                totalExp += item.getRepoHistoryExp();
            }

            repoEntity.updateExp(totalExp);
            repoHistoryRepository.saveAll(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("레포지토리 분석 종료 => {}", repoEntity.getRepoName());
    }

    private void updateRepositoryInfo(RepoEntity repoEntity, GHRepository ghRepository){
        log.info("기존 레포지토리 업데이트 시작 => {}", repoEntity.getRepoName());

        repoHistoryRepository.findFirstByRepoOrderByWorkedAtDesc(repoEntity).ifPresentOrElse(historyEntity -> {
            LocalDate workedAt = historyEntity.getWorkedAt();
            Date workDate = Date.from(workedAt.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Calendar cal = Calendar.getInstance();
            cal.setTime(workDate);
            cal.add(Calendar.DATE, 1);

            initRepositoryInfo(repoEntity, ghRepository, Date.from(cal.toInstant()));
        }, () -> {
            try {
                Date createdAt = ghRepository.getCreatedAt();
                initRepositoryInfo(repoEntity, ghRepository, createdAt);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        log.info("기존 레포지토리 업데이트 종료 => {}", repoEntity.getRepoName());
    }

}
