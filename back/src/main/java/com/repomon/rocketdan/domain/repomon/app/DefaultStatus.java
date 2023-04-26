package com.repomon.rocketdan.domain.repomon.app;


import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import lombok.Getter;

import java.util.HashMap;


@Getter
public class DefaultStatus {

	// 기본값
	public static final Integer defaultAtk = 20;
	public static final Float defaultDodge = 20f;
	public static final Float defaultDef = 0f;
	public static final Float defaultCritical = 10f;
	public static final Float defaultHit = 20f;
	public static final Integer defaultHp = 100;

	// 증가치
	public static final Integer atkValue = 2;
	public static final Float dodgeValue = 1f;
	public static final Float defValue = 1f;
	public static final Float criticalValue = 1f;
	public static final Float hitValue = 1f;
	public static final Float hpValue = 0.05f;


	public static Integer createAtk(Integer startPoint, Integer atkPoint) {
		return (startPoint + atkPoint) * atkValue + defaultAtk;
	}


	public static Float createDodge(Integer startPoint, Integer nowPoint) {
		return (startPoint + nowPoint) * dodgeValue + defaultDodge;
	}


	public static Float createDef(Integer startPoint, Integer nowPoint) {
		return (startPoint + nowPoint) * defValue + defaultDef;
	}


	public static Float createCritical(Integer startPoint, Integer nowPoint) {
		return (startPoint + nowPoint) * criticalValue + defaultCritical;
	}


	public static Float createHit(Integer startPoint, Integer nowPoint) {
		return (startPoint + nowPoint) * hitValue + defaultHit;
	}


	public static Integer createHp(Long exp) {

		return (int) (exp * hpValue) + defaultHp;
	}


	public static HashMap<String, Object> createStatus(RepomonStatusEntity repomon) {

		Integer attack = createAtk(repomon.getStartAtk(), repomon.getAtkPoint());
		Float dodge = createDodge(repomon.getStartDodge(), repomon.getDodgePoint());
		Float def = createDef(repomon.getStartDef(), repomon.getDefPoint());
		Float critical = createCritical(repomon.getStartCritical(), repomon.getCriticalPoint());
		Float hit = createHit(repomon.getStartHit(), repomon.getHitPoint());
		Integer hp = createHp(repomon.getRepoExp());

		return new HashMap<String, Object>() {
			{
				put("attack", attack);
				put("dodge", dodge);
				put("def", def);
				put("critical", critical);
				put("hit", hit);
				put("hp", hp);
			}
		};
	}

}
