package com.repomon.rocketdan.domain.repomon.service;

import com.repomon.rocketdan.domain.repomon.dto.RepomonStatusResponseDto;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import com.repomon.rocketdan.domain.repomon.repository.RepomonStatusRepository;
import com.repomon.rocketdan.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.repomon.rocketdan.exception.ErrorCode.NOT_FOUND_ENTITY;


@Service
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
     * 유저의 Rating을 기준으로 가장 가까운 유저를 검색
     *
     * @param repoId
     * @return
     */
    public RepomonStatusResponseDto getBattleTarget(Long repoId) {
        RepomonStatusEntity repomonStatus = repomonStatusRepository.findById(repoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_ENTITY)
        );
        Integer userRating = repomonStatus.getRating();

        // 나와 Rating이 가장 가까운 유저 매칭
        Pageable pageable = PageRequest.of(0, 2);
        List<RepomonStatusEntity> repomonList = repomonStatusRepository.findByRatingOrderByAbsRating(userRating, pageable).getContent();
        for (RepomonStatusEntity repomonStatusEntity : repomonList) {
            if (!repomonStatusEntity.equals(repomonStatus)) {
                return RepomonStatusResponseDto.fromEntity(repomonStatusEntity);
            }
        }

        return null;
    }
}
