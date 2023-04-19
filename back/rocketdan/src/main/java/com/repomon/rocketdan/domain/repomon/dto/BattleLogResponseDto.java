package com.repomon.rocketdan.domain.repomon.dto;


import com.repomon.rocketdan.domain.repomon.entity.BattleLogEntity;
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
public class BattleLogResponseDto {

	public static BattleLogResponseDto fromEntity(BattleLogEntity battleLog) {
		return new BattleLogResponseDto();
	}


	public static List<BattleLogResponseDto> fromEntityList(List<BattleLogEntity> battleLogList) {
		List<BattleLogResponseDto> result = new ArrayList<>();
		for (BattleLogEntity battleLog : battleLogList) {
			BattleLogResponseDto battleLogResponseDto = BattleLogResponseDto.fromEntity(battleLog);
			result.add(battleLogResponseDto);
		}
		return result;
	}
}
