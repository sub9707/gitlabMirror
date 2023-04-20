package com.repomon.rocketdan.domain.repomon.dto;


import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;




@AllArgsConstructor
@Getter
@ToString
@Builder
public class RepomonStatusResponseDto {

	public static RepomonStatusResponseDto fromEntity(RepomonStatusEntity repomonStatus) {
		return new RepomonStatusResponseDto();
	}


	public static List<RepomonStatusResponseDto> fromEntityList(List<RepomonStatusEntity> repomonStatusList) {
		List<RepomonStatusResponseDto> result = new ArrayList<>();
		for (RepomonStatusEntity repomonStatus : repomonStatusList) {
			RepomonStatusResponseDto repomonStatusResponseDto = RepomonStatusResponseDto.fromEntity(repomonStatus);
			result.add(repomonStatusResponseDto);
		}
		return result;
	}
}
