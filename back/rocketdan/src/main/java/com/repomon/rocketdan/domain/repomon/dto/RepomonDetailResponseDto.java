package com.repomon.rocketdan.domain.repomon.dto;


import com.repomon.rocketdan.domain.repomon.entity.RepomonDetailEntity;
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
public class RepomonDetailResponseDto {

	public static RepomonDetailResponseDto fromEntity(RepomonDetailEntity repomonDetail) {
		return new RepomonDetailResponseDto();
	}


	public static List<RepomonDetailResponseDto> fromEntityList(List<RepomonDetailEntity> repomonDetailList) {
		List<RepomonDetailResponseDto> result = new ArrayList<>();
		for (RepomonDetailEntity repomonDetail : repomonDetailList) {
			RepomonDetailResponseDto repomonDetailResponseDto = RepomonDetailResponseDto.fromEntity(repomonDetail);
			result.add(repomonDetailResponseDto);
		}
		return result;
	}
}
