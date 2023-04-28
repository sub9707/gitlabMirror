package com.repomon.rocketdan.domain.repo.dto.response;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class RepoRankResponseDto {

	private Long repoId;
	private String repomon;
	private String repoName;
	private Long repoExp;
	private String username;
	private String repoOwner;
	private String repomonNickname;
	private Integer rating;
	private Integer repomonTier;
	private String repoKey;


	public static RepoRankResponseDto fromEntity(RepoEntity repo) {
		return RepoRankResponseDto.builder()
			.repoId(repo.getRepoId())
			.repoName(repo.getRepoName())
			.repoExp(repo.getRepoExp())
			.repoOwner(repo.getRepoOwner())
			.repomonNickname(repo.getRepomonNickname())
			.rating(repo.getRating())
			.repomonTier(repo.getRepomon().getRepomonTier())
			.repoKey(repo.getRepoKey())
			.build();
	}


	public static List<RepoRankResponseDto> fromEntityList(List<RepoEntity> repoList) {
		List<RepoRankResponseDto> result = new ArrayList<>();
		for (RepoEntity repo : repoList) {
			RepoRankResponseDto repoRankResponseDto = RepoRankResponseDto.fromEntity(repo);
			result.add(repoRankResponseDto);
		}
		return result;
	}

}
