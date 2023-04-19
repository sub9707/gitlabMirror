package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.domain.user.entity.ActiveRepoEntity;
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
public class ActiveRepoResponseDto {

	public static ActiveRepoResponseDto fromEntity(ActiveRepoEntity activeRepo) {
		return new ActiveRepoResponseDto();
	}


	public static List<ActiveRepoResponseDto> fromEntityList(List<ActiveRepoEntity> activeRepoList) {
		List<ActiveRepoResponseDto> result = new ArrayList<>();
		for (ActiveRepoEntity activeRepo : activeRepoList) {
			ActiveRepoResponseDto activeRepoResponseDto = ActiveRepoResponseDto.fromEntity(activeRepo);
			result.add(activeRepoResponseDto);
		}
		return result;
	}
}
