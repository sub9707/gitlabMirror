package com.repomon.rocketdan.domain.repo.dto;


import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class RepomonResponseDto {
	private Long repomonId;
	private String repomonName;
	private Integer repomonSkill;

	public static RepomonResponseDto fromEntity(RepomonEntity repomon) {
		return RepomonResponseDto.builder()
				.repomonId(repomon.getRepomonId())
				.repomonName(repomon.getRepomonName())
				.repomonSkill(repomon.getRepomonSkill())
				.build();
	}


	public static List<RepomonResponseDto> fromEntityList(List<RepomonEntity> repomonList) {
		List<RepomonResponseDto> result = new ArrayList<>();
		for (RepomonEntity repomon : repomonList) {
			RepomonResponseDto repomonResponseDto = RepomonResponseDto.fromEntity(repomon);
			result.add(repomonResponseDto);
		}
		return result;
	}
}
