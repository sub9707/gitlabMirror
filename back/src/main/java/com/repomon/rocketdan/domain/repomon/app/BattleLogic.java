package com.repomon.rocketdan.domain.repomon.app;


import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import lombok.Getter;

import java.util.HashMap;
import java.util.Random;


@Getter
public class BattleLogic {

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
	public static final Float defValue = 0.5f;
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


	public static HashMap<String, Float> createStatus(RepomonStatusEntity repomon) {

		Float attack = (float) createAtk(repomon.getStartAtk(), repomon.getAtkPoint());
		Float dodge = createDodge(repomon.getStartDodge(), repomon.getDodgePoint());
		Float def = createDef(repomon.getStartDef(), repomon.getDefPoint());
		Float critical = createCritical(repomon.getStartCritical(), repomon.getCriticalPoint());
		Float hit = createHit(repomon.getStartHit(), repomon.getHitPoint());
		Float hp = (float) createHp(repomon.getRepoExp());

		return new HashMap<String, Float>() {
			{
				put("atk", attack);
				put("dodge", dodge);
				put("def", def);
				put("critical", critical);
				put("hit", hit);
				put("hp", hp);
			}
		};
	}


	/**
	 * 사용자가 투자한 전체 스텟 조회
	 *
	 * @param repomon
	 * @return
	 */
	public static Integer getAllStat(RepomonStatusEntity repomon) {
		return (repomon.getAtkPoint() + repomon.getDefPoint() + repomon.getDodgePoint()
			+ repomon.getCriticalPoint() + repomon.getHitPoint());
	}


	/**
	 * MMR 계산을 위한 스탯 차이 계산
	 *
	 * @param myRepomon
	 * @param yourRepomon
	 * @return
	 */
	public static Integer createGap(RepomonStatusEntity myRepomon,
		RepomonStatusEntity yourRepomon) {
		return ((getAllStat(myRepomon)
			+ (int) ((myRepomon.getRepoExp()) / 100))
			- (getAllStat(yourRepomon)
			+ (int) ((yourRepomon.getRepoExp()) / 100)));

	}


	/**
	 * 개별 레포몬 공격 데미지 계산 공식 * 올스탯에 비례한 랜덤 난수 * 80~120퍼센트의 데미지 * (1 - 상대 방어율)
	 *
	 * @param repomon
	 * @return
	 */
	public static Integer attackDamageCalc(RepomonStatusEntity repomon, Float def) {
		Random random = new Random();
		Integer allStat = getAllStat(repomon);
		Integer attack = createAtk(repomon.getStartAtk(), repomon.getAtkPoint());
		Integer randomDmg = random.nextInt((int) allStat / 2); // 전체 스텟의 25%만큼 랜덤 데미지 추가
		Float randomPercent = random.nextFloat() * 0.4f;
		return (int) (((attack + randomDmg) * (0.8f + randomPercent)) * (1 - def));
	}


	public static Integer skillDamageCalc(RepomonStatusEntity repomon) {
		Integer allStat = getAllStat(repomon);
		Integer attack = createAtk(repomon.getStartAtk(), repomon.getAtkPoint());
		return (attack + allStat) * 2;
	}

	//	public static HashMap<String, Object> battle(Integer turn, RepomonStatusEntity offenseRepomon, RepomonStatusEntity defenseRepomon){
	//
	//
	//	}


	public static HashMap<String, Object> useSkillLog(Integer turn, Long attackRepoId,
		Long defenseRepoId, Integer skillDmg) {
		return new HashMap<>() {
			{
				put("turn", turn);
				put("attacker", attackRepoId);
				put("defender", defenseRepoId);
				put("attack_act", "스킬");
				put("defense_act", "피격");
				put("damage", skillDmg);
			}
		};
	}

}
