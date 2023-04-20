package com.repomon.rocketdan.domain.repomon.entity;


import com.repomon.rocketdan.common.entity.CommonEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "attack_repo_id")
	private RepomonStatusEntity attackRepo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "defense_repo_id")
	private RepomonStatusEntity defenseRepo;

}
