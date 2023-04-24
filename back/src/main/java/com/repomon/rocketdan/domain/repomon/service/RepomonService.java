package com.repomon.rocketdan.domain.repomon.service;

import com.repomon.rocketdan.domain.repomon.dto.RepomonStatusResponseDto;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import com.repomon.rocketdan.domain.repomon.repository.RepomonStatusRepository;
import com.repomon.rocketdan.exception.CustomException;
import com.repomon.rocketdan.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.repomon.rocketdan.exception.ErrorCode.NOT_FOUND_ENTITY;


@Service
@RequiredArgsConstructor
public class RepomonService {

    private final RepomonStatusRepository repomonStatusRepository;

    /**
     * 해당 레포에 속한 레포몬의 상세 정보 반환
     *
     * @param repoId
     * @return
     */
    @Transactional
    public RepomonStatusResponseDto getRepomonInfo(Long repoId) {
        RepomonStatusEntity repomonStatus = repomonStatusRepository.findById(repoId).orElseThrow(
                () -> new CustomException(NOT_FOUND_ENTITY)
        );
        return RepomonStatusResponseDto.fromEntity(repomonStatus);

    }



}
