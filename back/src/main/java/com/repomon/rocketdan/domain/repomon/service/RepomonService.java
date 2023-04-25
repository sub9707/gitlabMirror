package com.repomon.rocketdan.domain.repomon.service;

import com.repomon.rocketdan.domain.repomon.dto.BattleLogResponseDto;
import com.repomon.rocketdan.domain.repomon.dto.RepomonStatusResponseDto;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import com.repomon.rocketdan.domain.repomon.repository.RepomonStatusRepository;
import com.repomon.rocketdan.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.repomon.rocketdan.exception.ErrorCode.NOT_FOUND_ENTITY;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RepomonService {

    private final RepomonStatusRepository repomonStatusRepository;

    /**
     * 해당 레포에 속한 레포몬의 상세 정보 반환
     *
     * @param repoId
     * @return
     */
    public RepomonStatusResponseDto getRepomonInfo(Long repoId) {
        RepomonStatusEntity repomonStatus = repomonStatusRepository.findById(repoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_ENTITY)
        );
        return RepomonStatusResponseDto.fromEntity(repomonStatus);

    }


    /**
     * 유저의 Rating을 기준으로 +-100 사이의 유저 검색
     *
     * @param repoId
     * @return
     */
    public RepomonStatusResponseDto getBattleTarget(Long repoId) {
        RepomonStatusEntity repomonStatus = repomonStatusRepository.findById(repoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_ENTITY)
        );
        Integer userRating = repomonStatus.getRating();
        int index = 1;
        while (index <= 10) {
            Integer startRating = userRating - (index * 200);
            Integer endRating = userRating + (index * 200);
            Optional<RepomonStatusEntity> repomon = repomonStatusRepository.findByRatingBetweenRandom(startRating, endRating, repoId);
            
            if (repomon.isPresent()) {
                return RepomonStatusResponseDto.fromEntity(repomon.get());
            }
            index += 1;

        }

        throw new CustomException(NOT_FOUND_ENTITY);

//        // 나와 Rating이 가장 가까운 유저 매칭
//        Pageable pageable = PageRequest.of(0, 2);
//        List<RepomonStatusEntity> repomonList = repomonStatusRepository.findByRatingOrderByAbsRating(userRating, pageable).getContent();
//        for (RepomonStatusEntity repomonStatusEntity : repomonList) {
//            if (!repomonStatusEntity.equals(repomonStatus)) {
//                return RepomonStatusResponseDto.fromEntity(repomonStatusEntity);
//            }
//        }
//
    }

    /**
     * 최근순으로 5개의 전투결과를 조회합니다.
     *
     * @param repoId
     * @return
     */
    public BattleLogResponseDto getBattleLogList(Long repoId) {


        return null;
    }
}
