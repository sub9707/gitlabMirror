package com.repomon.rocketdan.domain.user.dto.response;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class RepoRankResponseDto {

	private Long repoId;
	private String repoName;
	private Long repoExp;
	private String repoOwner;
	private String repomonNickname;
	private Integer rating;
	private Integer repomonTier;
	private Long repoRank;
	private String repomonUrl;


	public static RepoRankResponseDto fromEntity(RepoEntity repo, Long repoRank) {
		return RepoRankResponseDto.builder()
			.repoId(repo.getRepoId())
			.repoName(repo.getRepoName())
			.repoExp(repo.getRepoExp())
			.repoOwner(repo.getRepoOwner())
			.repomonNickname(repo.getRepomonNickname())
			.rating(repo.getRating())
			.repomonTier(repo.getRepomon().getRepomonTier())
			.repoRank(repoRank)
			.repomonUrl(repo.getRepomon().getRepomonUrl())
			.build();
	}

}
