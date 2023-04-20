package com.repomon.rocketdan.domain.repomon.dto;


import com.repomon.rocketdan.domain.repomon.entity.BattleLogEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;




@AllArgsConstructor
@Getter
@ToString
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
