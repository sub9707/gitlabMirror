package com.repomon.rocketdan.domain.repomon.service;


import com.repomon.rocketdan.domain.repomon.dto.BattleLogListResponseDto;
import com.repomon.rocketdan.domain.repomon.dto.RepomonStatusRequestDto;
import com.repomon.rocketdan.domain.repomon.dto.RepomonStatusResponseDto;
import com.repomon.rocketdan.domain.repomon.entity.BattleLogEntity;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import com.repomon.rocketdan.domain.repomon.repository.BattleLogRepository;
import com.repomon.rocketdan.domain.repomon.repository.RepomonStatusRepository;
import com.repomon.rocketdan.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.repomon.rocketdan.exception.ErrorCode.NOT_FOUND_ENTITY;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RepomonService {

	private final BattleLogRepository battleLogRepository;

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
	 * 레포몬의 초기 닉네임, 스텟 정보 등록
	 *
	 * @param repomonStatusRequestDto
	 */
	public void createRepomonStatus(RepomonStatusRequestDto repomonStatusRequestDto) {
		RepomonStatusEntity repomon = repomonStatusRepository.findById(
			repomonStatusRequestDto.getRepoId()).orElseThrow(
			() -> new CustomException(NOT_FOUND_ENTITY)
		);
		repomon.updateNickname(repomonStatusRequestDto.getRepomonNickname());
		repomon.setStartStatus(repomonStatusRequestDto.getAtkPoint(),
			repomonStatusRequestDto.getDodgePoint(),
			repomonStatusRequestDto.getDefPoint(),
			repomonStatusRequestDto.getCriticalPoint(),
			repomonStatusRequestDto.getHitPoint()
		);
		repomonStatusRepository.save(repomon);
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
			Optional<RepomonStatusEntity> repomon = repomonStatusRepository.findByRatingBetweenRandom(
				startRating, endRating, repoId);

			if (repomon.isPresent()) {
				return RepomonStatusResponseDto.fromEntity(repomon.get());
			}
			index += 1;

		}

		throw new CustomException(NOT_FOUND_ENTITY);

	}


	/**
	 * 최근순으로 5개의 전투결과를 조회합니다.
	 *
	 * @param repoId
	 * @return
	 */
	public BattleLogListResponseDto getBattleLogList(Long repoId) {
		List<BattleLogEntity> battleLogList = battleLogRepository.findTop5ByRepoIdOrderByDesc(
			repoId);
		if (battleLogList.isEmpty()) {
			throw new CustomException(NOT_FOUND_ENTITY);
		}
		BattleLogListResponseDto battleLogListResponseDto = BattleLogListResponseDto.fromEntity(
			battleLogList);

		return battleLogListResponseDto;
	}


	public void modifyRepomonStatus(RepomonStatusRequestDto repomonStatusRequestDto) {
		RepomonStatusEntity repomon = repomonStatusRepository.findById(
			repomonStatusRequestDto.getRepoId()).orElseThrow(
			() -> new CustomException(NOT_FOUND_ENTITY)
		);

		repomon.updateStatus(repomonStatusRequestDto.getAtkPoint(),
			repomonStatusRequestDto.getDodgePoint(),
			repomonStatusRequestDto.getDefPoint(),
			repomonStatusRequestDto.getCriticalPoint(),
			repomonStatusRequestDto.getHitPoint());

		repomonStatusRepository.save(repomon);
	}


	public void modifyRepomonNickname(RepomonStatusRequestDto repomonStatusRequestDto) {
		RepomonStatusEntity repomon = repomonStatusRepository.findById(
			repomonStatusRequestDto.getRepoId()).orElseThrow(
			() -> new CustomException(NOT_FOUND_ENTITY)
		);
		repomon.updateNickname(repomonStatusRequestDto.getRepomonNickname());
		repomonStatusRepository.save(repomon);

	}

}
