package com.repomon.rocketdan.domain.repo.dto;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepoResponseDto {

	public static RepoResponseDto fromEntity(RepoEntity repo) {
		return new RepoResponseDto();
	}


	public static List<RepoResponseDto> fromEntityList(List<RepoEntity> repoList) {
		List<RepoResponseDto> result = new ArrayList<>();
		for (RepoEntity repo : repoList) {
			RepoResponseDto repoResponseDto = RepoResponseDto.fromEntity(repo);
			result.add(repoResponseDto);
		}
		return result;
	}
}
