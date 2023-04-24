package com.repomon.rocketdan.domain.repomon.dto;


import com.repomon.rocketdan.domain.repomon.entity.BattleLogEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class BattleLogResponseDto {

	private Boolean isWin;
	private Integer attackPoint;
	private Integer defensePoint;
	private List<Map<String, Object>> battleLog;
	private RepomonStatusResponseDto attackRepo;
	private RepomonStatusResponseDto defenseRepo;

	private List<BattleLog> battleLogList;


	public static BattleLogResponseDto fromEntity(BattleLogEntity battleLog) {
		return new BattleLogResponseDto();
	}


	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	private static class BattleLog {
		private Boolean isWin;
		private Integer attackPoint;
		private Integer defensePoint;
		private RepomonStatusResponseDto attackRepo;
		private RepomonStatusResponseDto defenseRepo;

		public static BattleLog fromEntity(BattleLogEntity battleLog) {
			return BattleLog.builder()
					.isWin(battleLog.getIsWin())
					.attackPoint(battleLog.getAttackPoint())
					.defensePoint(battleLog.getDefensePoint())
					.attackRepo(RepomonStatusResponseDto.fromEntity(battleLog.getAttackRepo()))
					.defenseRepo(RepomonStatusResponseDto.fromEntity(battleLog.getDefenseRepo()))
					.build();
		}

	}
}
