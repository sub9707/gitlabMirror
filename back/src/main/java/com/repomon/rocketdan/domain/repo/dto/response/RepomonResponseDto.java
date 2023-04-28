package com.repomon.rocketdan.domain.repo.dto.response;


import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class RepomonResponseDto {

	private Long repomonId;
	private String repomonUrl;
	private String repomonName;
	private Integer repomonSkill;
	private Integer repomonTier;
	private List<SelectRepomon> selectRepomonList;


	public static RepomonResponseDto fromEntity(RepomonEntity repomon) {
		return RepomonResponseDto.builder()
			.repomonId(repomon.getRepomonId())
			.repomonName(repomon.getRepomonName())
			.repomonUrl(repomon.getRepomonUrl())
			.repomonTier(repomon.getRepomonTier())
			.repomonSkill(repomon.getRepomonSkill())
			.build();
	}


	public static RepomonResponseDto createSelectRepomon(List<RepomonEntity> repomonList) {
		List<SelectRepomon> selectRepomons = repomonList.stream()
			.map(SelectRepomon::fromEntity)
			.collect(Collectors.toList());

		return RepomonResponseDto.builder()
			.selectRepomonList(selectRepomons)
			.build();
	}


	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class SelectRepomon {

		private Long repomonId;
		private String repomonUrl;
		private String repomonName;


		public static SelectRepomon fromEntity(RepomonEntity repomon) {
			return SelectRepomon.builder()
				.repomonId(repomon.getRepomonId())
				.repomonName(repomon.getRepomonName())
				.repomonUrl(repomon.getRepomonUrl())
				.build();
		}

	}

}
