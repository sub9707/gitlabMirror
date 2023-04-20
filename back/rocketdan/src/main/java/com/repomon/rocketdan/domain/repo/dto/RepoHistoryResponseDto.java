package com.repomon.rocketdan.domain.repo.dto;


import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
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
public class RepoHistoryResponseDto {

	public static RepoHistoryResponseDto fromEntity(RepoHistoryEntity repoHistory) {
		return new RepoHistoryResponseDto();
	}

	public static List<RepoHistoryResponseDto> fromEntityList(List<RepoHistoryEntity> repoHistoryList) {
		List<RepoHistoryResponseDto> result = new ArrayList<>();
		for (RepoHistoryEntity repoHistory : repoHistoryList) {
			RepoHistoryResponseDto repoHistoryResponseDto = RepoHistoryResponseDto.fromEntity(repoHistory);
			result.add(repoHistoryResponseDto);
		}
		return result;
	}

}
