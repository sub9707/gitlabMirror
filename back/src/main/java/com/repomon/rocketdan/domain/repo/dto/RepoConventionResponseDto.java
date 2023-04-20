package com.repomon.rocketdan.domain.repo.dto;


import com.repomon.rocketdan.domain.repo.entity.RepoConventionEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;




@AllArgsConstructor
@Getter
@ToString
@Builder
public class RepoConventionResponseDto {

	public static RepoConventionResponseDto fromEntity(RepoConventionEntity repoConvention) {
		return new RepoConventionResponseDto();
	}


	public static List<RepoConventionResponseDto> fromEntityList(List<RepoConventionEntity> repoConventionList) {
		List<RepoConventionResponseDto> result = new ArrayList<>();
		for (RepoConventionEntity repoConvention : repoConventionList) {
			RepoConventionResponseDto repoConventionResponseDto = RepoConventionResponseDto.fromEntity(repoConvention);
			result.add(repoConventionResponseDto);
		}

		return result;
	}
}
