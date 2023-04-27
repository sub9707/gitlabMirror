package com.repomon.rocketdan.domain.user.service;


import com.repomon.rocketdan.domain.repo.dto.response.RepoRankResponseDto;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class RankService {

	private final RepoRepository repoRepository;


	public void getUserRankList(String search, Pageable pageable) {

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
			Page<RepoEntity> repoEntitiyList = repoRepository.findByIsActiveTrue(pageable);
			return repoEntitiyList.map(RepoRankResponseDto::fromEntity);
		} else {
			Page<RepoEntity> repoEntitiyList = repoRepository.findByRepoNameContainingAndIsActiveTrue(search, pageable);
			return repoEntitiyList.map(RepoRankResponseDto::fromEntity);
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
			Page<RepoEntity> repoEntitiyList = repoRepository.findByIsActiveTrue(pageable);
			return repoEntitiyList.map(RepoRankResponseDto::fromEntity);
		} else {
			Page<RepoEntity> repoEntitiyList = repoRepository.findByRepoNameContainingAndIsActiveTrue(search, pageable);
			return repoEntitiyList.map(RepoRankResponseDto::fromEntity);
		}
	}

}
