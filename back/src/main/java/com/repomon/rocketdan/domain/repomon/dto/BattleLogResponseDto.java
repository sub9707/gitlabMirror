package com.repomon.rocketdan.domain.repomon.dto;


import com.repomon.rocketdan.domain.repomon.entity.BattleLogEntity;
import lombok.*;

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


    public static BattleLogResponseDto fromEntity(BattleLogEntity battleLog) {
        return BattleLogResponseDto.builder()
                .isWin(battleLog.getIsWin())
                .attackPoint(battleLog.getAttackPoint())
                .defensePoint(battleLog.getDefensePoint())
                .attackRepo(RepomonStatusResponseDto.fromEntity(battleLog.getAttackRepo()))
                .defenseRepo(RepomonStatusResponseDto.fromEntity(battleLog.getDefenseRepo()))
                .build();
    }


}
