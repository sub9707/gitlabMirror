package com.repomon.rocketdan.domain.repo.app;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepoListItem {
	private Long repoId;
	private Long repomonId;
	private String repoName;
	private String repomonName;
	private String repoDescription;
	private Long repoExp;
	private Integer repoRating;
	private Boolean isActive;
	private Boolean isPrivate;

	public static RepoListItem convertFromDetail(RepoDetail repoDetail){
		RepoEntity repo = repoDetail.getRepoEntity();
		RepomonEntity repomon = repo.getRepomon();

		return RepoListItem.builder()
			.repoId(repo.getRepoId())
			.repomonId(repomon == null ? null : repomon.getRepomonId())
			.repoName(repo.getRepoName())
			.repomonName(repo.getRepomonNickname())
			.repoDescription(repoDetail.getDescription())
			.repoExp(repo.getRepoExp())
			.repoRating(repo.getRating())
			.isActive(repoDetail.getIsActive())
			.isPrivate(repoDetail.getIsPrivate())
			.build();
	}
}
