package com.repomon.rocketdan.domain.repo.dto;


import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
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
public class RepomonResponseDto {

	public static RepomonResponseDto fromEntity(RepomonEntity repomon) {
		return new RepomonResponseDto();
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
