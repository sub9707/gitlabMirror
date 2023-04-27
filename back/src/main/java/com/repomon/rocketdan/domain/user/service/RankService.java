package com.repomon.rocketdan.domain.user.service;


import com.repomon.rocketdan.domain.repo.dto.response.RepoRankResponseDto;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.repository.ActiveRepoRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import com.repomon.rocketdan.domain.user.dto.UserRankResponseDto;
import com.repomon.rocketdan.domain.user.entity.UserCardEntity;
import com.repomon.rocketdan.domain.user.repository.UserCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class RankService {

	private final RepoRepository repoRepository;
	private final UserCardRepository userCardRepository;
	private final ActiveRepoRepository activeRepoRepository;


	/**
	 * 유저 랭킹 조회(활성 레포 총 경험치 합)
	 *
	 * @param search   : 유저이름 포함 문자열
	 * @param pageable : 페이지네이션 size, page
	 * @return
	 */
	public Page<UserRankResponseDto> getUserRankList(String search, Pageable pageable) {

		List<UserRankResponseDto> userRankResponseDtoList = new ArrayList<>();
		Page<UserCardEntity> userCardEntityList;
		if (search.isEmpty()) {
			userCardEntityList = userCardRepository.findAll(pageable);
		} else {
			userCardEntityList = userCardRepository.findByUserNameContaining(search, pageable);
		}
		for (UserCardEntity userCard : userCardEntityList) {
			Long repoCount = activeRepoRepository.countByUser_UserId(userCard.getUserId());
			UserRankResponseDto userRankResponseDto = UserRankResponseDto.fromEntity(userCard);
			userRankResponseDto.setRepoCount(repoCount);
			userRankResponseDtoList.add(userRankResponseDto);
		}
		return new PageImpl<>(userRankResponseDtoList, pageable, userCardEntityList.getTotalElements());
	}


	/**
	 * 레포 랭킹 조회(경험치)
	 *
	 * @param search   : 레포이름 포함 문자열
	 * @param pageable : 페이지네이션 size, page
	 * @return
	 */
	public Page<RepoRankResponseDto> getRepoRankList(String search, Pageable pageable) {
		if (search.isEmpty()) {
			Page<RepoEntity> repoEntityList = repoRepository.findByIsActiveTrue(pageable);
			return repoEntityList.map(RepoRankResponseDto::fromEntity);
		} else {
			Page<RepoEntity> repoEntityList = repoRepository.findByRepoNameContainingAndIsActiveTrue(search, pageable);
			return repoEntityList.map(RepoRankResponseDto::fromEntity);
		}
	}


	/**
	 * 레포몬 랭킹 조회(레이팅)
	 *
	 * @param search   : 레포이름 포함 문자열
	 * @param pageable : 페이지네이션 size, page
	 * @return
	 */
	public Page<RepoRankResponseDto> getRepomonRankList(String search, Pageable pageable) {
		if (search.isEmpty()) {
			Page<RepoEntity> repoEntityList = repoRepository.findByIsActiveTrue(pageable);
			return repoEntityList.map(RepoRankResponseDto::fromEntity);
		} else {
			Page<RepoEntity> repoEntityList = repoRepository.findByRepoNameContainingAndIsActiveTrue(search, pageable);
			return repoEntityList.map(RepoRankResponseDto::fromEntity);
		}
	}

}
