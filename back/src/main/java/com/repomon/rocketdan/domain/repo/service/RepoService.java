package com.repomon.rocketdan.domain.repo.service;


import com.repomon.rocketdan.common.utils.GHUtils;
import com.repomon.rocketdan.common.utils.SecurityUtils;
import com.repomon.rocketdan.domain.repo.app.RepoDetail;
import com.repomon.rocketdan.domain.repo.dto.request.RepoConventionRequestDto;
import com.repomon.rocketdan.domain.repo.dto.request.RepoPeriodRequestDto;
import com.repomon.rocketdan.domain.repo.dto.response.*;
import com.repomon.rocketdan.domain.repo.entity.*;
import com.repomon.rocketdan.domain.repo.repository.*;
import com.repomon.rocketdan.domain.repo.repository.redis.RepoRedisContributeRepository;
import com.repomon.rocketdan.domain.repo.repository.redis.RepoRedisConventionRepository;
import com.repomon.rocketdan.domain.repo.repository.redis.RepoRedisListRepository;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import com.repomon.rocketdan.domain.repomon.repository.RepomonStatusRepository;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import com.repomon.rocketdan.domain.user.service.RankService;
import com.repomon.rocketdan.exception.CustomException;
import com.repomon.rocketdan.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHRepositoryStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service @Slf4j
@Transactional
@RequiredArgsConstructor
public class RepoService {

    private final GHUtils ghUtils;
    private final RankService rankService;
    private final UserRepository userRepository;
    private final RepoRepository repoRepository;
    private final RepomonRepository repomonRepository;
    private final RepomonStatusRepository repomonStatusRepository;
    private final RepoHistoryRepository repoHistoryRepository;
    private final RepoConventionRepository conventionRepository;
    private final ActiveRepoRepository activeRepoRepository;

    // redis repository
    private final RepoRedisListRepository redisListRepository;
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
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        });

        String userName = userEntity.getUserName();

        PageRequest redisPageable = PageRequest.of(pageable.getPageNumber(), 1);
        List<RepoListResponseDto> dtoList = redisListRepository.findByUserName(userName, redisPageable);
        RepoListResponseDto responseDto = dtoList.isEmpty() ?
            RepoListResponseDto.empty(userName) :
            dtoList.get(0);

        if(responseDto.getRepoListItems().size() < pageable.getPageSize()){
            Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(userName);

            saveAndUpdateRepo(repositories, userEntity);

            Page<ActiveRepoEntity> activeRepoPage = activeRepoRepository.findByUser(userEntity,
                pageable);
            List<RepoDetail> repoDetails = activeRepoPage.stream()
                .map(activeRepoEntity -> {
                    RepoEntity repoEntity = activeRepoEntity.getRepo();
                    GHRepository ghRepository = repositories.get(repoEntity.getRepoKey());
                    return ActiveRepoEntity.convertToRepo(activeRepoEntity, ghRepository);
                }).collect(Collectors.toList());

            long totalElements = activeRepoPage.getTotalElements();
            int totalPages = activeRepoPage.getTotalPages();

            responseDto.updateFromDetails(repoDetails, totalElements, totalPages);

            if(responseDto.getId() == null) {
                redisListRepository.save(responseDto);
            }
        }

        return responseDto;
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
        if(ghRepository == null){
            throw new CustomException(ErrorCode.NOT_FOUND_PUBLIC_REPOSITORY);
        }

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

        Long rank = rankService.getRepoRank(repoEntity);


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

        Long rank = rankService.getRepomonRank(repomonStatusEntity);

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
            .orElseGet(() -> findConventionDtoWithGHApi(repoEntity, repoOwner));

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

        List<ActiveRepoEntity> activeRepoEntities = activeRepoRepository.findAllByRepo(repoEntity);
        List<UserEntity> userEntities = activeRepoEntities.stream()
            .map(entity -> entity.getUser())
            .collect(Collectors.toList());

        String repoOwner = repoEntity.getRepoOwner();
        Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(repoOwner);

        String repoKey = repoEntity.getRepoKey();
        GHRepository ghRepository = repositories.get(repoKey);
        if(ghRepository == null){
            throw new CustomException(ErrorCode.NOT_FOUND_PUBLIC_REPOSITORY);
        }

        updateRepositoryInfo(repoEntity, ghRepository, userEntities);
    }


    /**
     * 레포 기간 설정
     * @param repoId
     * @param requestDto
     */
    public void modifyRepoPeriod(Long repoId, RepoPeriodRequestDto requestDto) {

	    // 권한 검증
	    String repoOwner = getRepoOwnerByRepoId(repoId);
	    if (!SecurityUtils.getCurrentUserId().equals(repoOwner)) {
		    throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
	    }

	    String userName = SecurityUtils.getCurrentUserId();

	    RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
		    throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
	    });

	    if (!repoEntity.getRepoOwner().equals(userName)) {
		    throw new CustomException(ErrorCode.NO_ACCESS);
	    }

	    LocalDateTime startedAt = requestDto.getStartedAt();
	    LocalDateTime endAt = requestDto.getEndAt();
	    repoEntity.updatePeriod(startedAt, endAt);
    }

    /**
     * 레포 컨벤션 수정 및 등록
     * @param repoId
     * @param requestDto
     */
    public void modifyRepoConvention(Long repoId, RepoConventionRequestDto requestDto) {

	    // 권한 검증
	    String repoOwner = getRepoOwnerByRepoId(repoId);
	    if (!SecurityUtils.getCurrentUserId().equals(repoOwner)) {
		    throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
	    }

	    String userName = SecurityUtils.getCurrentUserId();

	    RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
		    throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
	    });

	    if (!repoEntity.getRepoOwner().equals(userName)) {
		    throw new CustomException(ErrorCode.NO_ACCESS);
	    }

	    conventionRepository.deleteAllByRepo(repoEntity);
	    List<RepoConventionEntity> entities = requestDto.toEntities(repoEntity);
	    conventionRepository.saveAll(entities);
    }

    /**
     * 레포 활성화
     * @param repoId
     */
    public void activateRepo(Long repoId) {

	    // 권한 검증
	    String repoOwner = getRepoOwnerByRepoId(repoId);
	    if (!SecurityUtils.getCurrentUserId().equals(repoOwner)) {
		    throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
	    }

	    String userName = SecurityUtils.getCurrentUserId();

	    RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
		    throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
	    });

	    if (!repoEntity.getRepoOwner().equals(userName)) {
		    throw new CustomException(ErrorCode.NO_ACCESS);
	    }

	    if (repoEntity.getIsActive()) {
		    repoEntity.deActivate();
	    } else {
		    repoEntity.activate();
	    }
    }

    private void saveAndUpdateRepo(Map<String, GHRepository> repositories, UserEntity userEntity) {
        repositories.forEach((repoKey, ghRepository) -> {
            repoRepository.findByRepoKey(repoKey).ifPresentOrElse(repoEntity -> repoEntity.update(ghRepository),
                    () -> {
                        RepomonEntity repomonEntity = repomonRepository.findById(9999L)
                            .orElseThrow(() -> {
                                throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
                            });

                        RepomonStatusEntity repomonStatusEntity = RepomonStatusEntity.fromGHRepository(ghRepository, repomonEntity);
                        repomonStatusRepository.save(repomonStatusEntity);
                        activeRepoRepository.save(ActiveRepoEntity.of(userEntity, repomonStatusEntity));

                        Long exp = initRepositoryInfo(repomonStatusEntity, ghRepository, null);
                        userEntity.updateTotalExp(exp);
                    });
        });
    }


	private RepoContributeResponseDto findContributeDtoWithGHApi(RepoEntity repoEntity, String repoOwner) {
		Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(repoOwner);

		String repoKey = repoEntity.getRepoKey();
		GHRepository ghRepository = repositories.get(repoKey);
		if (ghRepository == null) {
			throw new CustomException(ErrorCode.NOT_FOUND_PUBLIC_REPOSITORY);
		}

		try {
            GHRepositoryStatistics statistics = ghRepository.getStatistics();

            long totalLineCount = ghUtils.getTotalLineCount(statistics);
            Map<String, Integer> commitCountMap = ghUtils.getCommitterInfoMap(statistics);

            String mvp = null;
            int totalCommitCount = 0;
            for (String user : commitCountMap.keySet()) {
				if (mvp == null || commitCountMap.get(user) > commitCountMap.get(mvp)) {
					mvp = user;
				}
                totalCommitCount += commitCountMap.get(user);
			}

			RepoContributeResponseDto responseDto = RepoContributeResponseDto.of(totalCommitCount, totalLineCount, commitCountMap, mvp, repoOwner);

			redisContributeRepository.save(responseDto);
			return responseDto;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


	private RepoConventionResponseDto findConventionDtoWithGHApi(RepoEntity repoEntity, String repoOwner) {
		List<RepoConventionEntity> conventions = conventionRepository.findAllByRepo(repoEntity);

		int totalCnt = 0;
		int collectCnt = 0;
		if (!conventions.isEmpty()) {
			log.info("컨벤션 분석 시작");

			Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(
				repoOwner);

			GHRepository ghRepository = repositories.get(repoEntity.getRepoKey());
			if (ghRepository == null) {
				throw new CustomException(ErrorCode.NOT_FOUND_PUBLIC_REPOSITORY);
			}

			try {
				List<GHCommit> ghCommits = ghRepository.listCommits().toList();
				for (GHCommit commit : ghCommits) {
					totalCnt++;
					String message = commit.getCommitShortInfo().getMessage();

					boolean present = conventions.stream().anyMatch(convention -> {
						String prefix = convention.getRepoConventionType();
						return message.startsWith(prefix);
					});

					if (present) {
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
    private Long initRepositoryInfo(RepoEntity repoEntity, GHRepository ghRepository, Date fromDate) {
        log.info("레포지토리 분석 시작 => {}", repoEntity.getRepoName());
        try {
            List<RepoHistoryEntity> list = new ArrayList<>();
            list.addAll(ghUtils.GHCommitToHistory(ghRepository, repoEntity, fromDate));
            list.addAll(ghUtils.GHPullRequestToHistory(ghRepository, repoEntity, fromDate));
            list.addAll(ghUtils.GHIssueToHistory(ghRepository, repoEntity, fromDate));

            // 효율성 ? 보안성 ?

            Long totalExp = 0L;
            for(RepoHistoryEntity item : list){
                totalExp += item.getRepoHistoryExp();
            }

            repoEntity.updateExp(totalExp);
            repoHistoryRepository.saveAll(list);

            log.info("레포지토리 분석 종료 => {}", repoEntity.getRepoName());

            return totalExp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateRepositoryInfo(RepoEntity repoEntity, GHRepository ghRepository, List<UserEntity> userEntities){
        log.info("기존 레포지토리 업데이트 시작 => {}", repoEntity.getRepoName());

		repoHistoryRepository.findFirstByRepoOrderByWorkedAtDesc(repoEntity).ifPresentOrElse(historyEntity -> {
			LocalDate workedAt = historyEntity.getWorkedAt();
			Date workDate = Date.from(workedAt.atStartOfDay(ZoneId.systemDefault()).toInstant());
			Calendar cal = Calendar.getInstance();
			cal.setTime(workDate);
			cal.add(Calendar.DATE, 1);

            Long exp = initRepositoryInfo(repoEntity, ghRepository, Date.from(cal.toInstant()));
            userEntities.forEach(userEntity -> userEntity.updateTotalExp(exp));
            repoEntity.updateExp(exp);
        }, () -> {
            Long exp = initRepositoryInfo(repoEntity, ghRepository, null);
            userEntities.forEach(userEntity -> userEntity.updateTotalExp(exp));
            repoEntity.updateExp(exp);
        });

		log.info("기존 레포지토리 업데이트 종료 => {}", repoEntity.getRepoName());
	}


	public Boolean checkRepomonNickname(String nickName) {
		return repoRepository.existsByRepomonNickname(nickName);
	}


	public RepomonResponseDto createSelectRepomon() {
		List<RepomonEntity> repomonList = repomonRepository.findTop3ByRandom();
		return RepomonResponseDto.createSelectRepomon(repomonList);
	}


    /**
     * 레포 detail
     * 레포 이름
     * 레포 기간
     * 레포 포크, 스타
     * 레포 description
     * 레포 언어
     * 전체 커밋, 코드, 보안성, 효율성
     * 레포몬, 전체 경험치
     * 컨트리뷰터 수
     */
    public RepoCardResponseDto RepoCardDetail(Long repoId) {
	    RepoEntity repoEntity = repoRepository.findById(repoId).orElseThrow(() -> {
		    throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
	    });

	    String repoOwner = repoEntity.getRepoOwner();

	    String repoKey = repoEntity.getRepoKey();
	    Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(repoOwner);
	    GHRepository ghRepository = repositories.get(repoKey);
	    if (ghRepository == null) {
		    throw new CustomException(ErrorCode.NOT_FOUND_PUBLIC_REPOSITORY);
	    }

	    return null;
	    //        return RepoCardResponseDto.fromEntityAndGHRepository(repoEntity, ghRepository);
    }


	public String getRepoOwnerByRepoId(Long repoId) {
		RepoEntity repo = repoRepository.findById(repoId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REPOSITORY));
		return repo.getRepoOwner();
	}

}
