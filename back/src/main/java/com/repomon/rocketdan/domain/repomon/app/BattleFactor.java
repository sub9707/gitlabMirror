package com.repomon.rocketdan.domain.repomon.app;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum BattleFactor {
	ATTACK(1, "attack"), CRITICAL(2, "critical"), SKILL(3, "skill"), DODGE(1, "dodge"), ATTACKED(2, "attacked");

	Integer idx;
	String act;

}
