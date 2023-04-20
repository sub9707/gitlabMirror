package com.repomon.rocketdan.domain.repomon.entity;


import com.repomon.rocketdan.common.entity.CommonEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BattleLogEntity extends CommonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "battle_log_id")
	private Long battleLogId;

	private Boolean isWin;
	private Integer attackPoint;
	private Integer defensePoint;

	private Long attackRepoId;
	private Long defenseRepoId;

}
