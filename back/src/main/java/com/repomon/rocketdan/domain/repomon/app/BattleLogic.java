package com.repomon.rocketdan.domain.repomon.app;


import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Random;

import static com.repomon.rocketdan.domain.repomon.app.BattleFactor.*;


@Getter
@Slf4j
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

	// 레이팅 최대값
	public static final Integer maxRating = 50;

	// 스킬 발동 확률
	public static final Integer skillProbability = 5;


	// 현재 해당 스텟 계산
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

		return new HashMap<>() {
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
		return Math.min((repomon.getAtkPoint() + repomon.getDefPoint() + repomon.getDodgePoint()
			+ repomon.getCriticalPoint() + repomon.getHitPoint() + (int) ((repomon.getRepoExp()) / 100)) + 1, 1);
	}


	/**
	 * MMR 계산을 위한 스탯 차이 계산
	 *
	 * @param offenseRepomon
	 * @param defenseRepomon
	 * @return
	 */
	public static Integer createGap(RepomonStatusEntity offenseRepomon,
		RepomonStatusEntity defenseRepomon) {
		return getAllStat(defenseRepomon) - getAllStat(offenseRepomon);

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
		float defense = Math.min(def, 90);
		Integer attack = createAtk(repomon.getStartAtk(), repomon.getAtkPoint());

		Integer randomDmg = random.nextInt(allStat / 2); // 전체 스텟의 50%만큼 랜덤 데미지 추가
		float randomPercent = random.nextFloat() * 0.4f;
		return (int) (((attack + randomDmg) * (0.8f + randomPercent)) * (1 - (defense
			/ 100)));

	}


	/**
	 * 스킬 데미지 계산
	 *
	 * @param repomon
	 * @return
	 */
	public static Integer skillDamageCalc(RepomonStatusEntity repomon) {
		Integer allStat = getAllStat(repomon);
		Integer attack = createAtk(repomon.getStartAtk(), repomon.getAtkPoint());
		return (attack + allStat) * 2;
	}


	public static HashMap<String, Object> battle(Integer turn, RepomonStatusEntity offenseRepomon,
		RepomonStatusEntity defenseRepomon, Integer skillDmg) {
		HashMap<String, Float> offenseStatus = createStatus(offenseRepomon);
		HashMap<String, Float> defenseStatus = createStatus(defenseRepomon);
		Random random = new Random();

		int isSkilled = random.nextInt(100);
		// 스킬 발동 여부 확인
		if (isSkilled < skillProbability) {
			return useSkillLog(turn, offenseRepomon.getRepoId(), defenseRepomon.getRepoId(),
				skillDmg);

		} else {
			// 명중 여부 확인
			float dodgePercent = Math.min(defenseStatus.get("dodge") - offenseStatus.get("hit"), 90);
			int isDodge = random.nextInt(100);
			boolean dodge = (isDodge < dodgePercent);

			// 치명타 여부 확인
			int isCritical = random.nextInt(100);
			if (isCritical < offenseStatus.get("critical")) {
				Integer dmg = attackDamageCalc(offenseRepomon, defenseStatus.get("def")) * 2;
				return dodge
					? useDodgeLog(turn, offenseRepomon.getRepoId(), defenseRepomon.getRepoId(), CRITICAL)
					: useAttackLog(turn, offenseRepomon.getRepoId(), defenseRepomon.getRepoId(), CRITICAL,
						dmg);
			} else {
				Integer dmg = attackDamageCalc(offenseRepomon, defenseStatus.get("def"));
				return dodge
					? useDodgeLog(turn, offenseRepomon.getRepoId(), defenseRepomon.getRepoId(), ATTACK)
					: useAttackLog(turn, offenseRepomon.getRepoId(), defenseRepomon.getRepoId(), ATTACK,
						dmg);
			}

		}

	}


	public static HashMap<String, Object> useAttackLog(Integer turn, Long attackRepoId,
		Long defenseRepoId, BattleFactor battleFactor, Integer dmg) {
		return new HashMap<>() {
			{
				put("turn", turn);
				put("attacker", attackRepoId);
				put("defender", defenseRepoId);
				put("attack_act", battleFactor.idx);
				put("defense_act", ATTACKED.idx);
				put("damage", dmg);
			}
		};
	}


	public static HashMap<String, Object> useDodgeLog(Integer turn, Long attackRepoId,
		Long defenseRepoId, BattleFactor battleFactor) {
		return new HashMap<>() {
			{
				put("turn", turn);
				put("attacker", attackRepoId);
				put("defender", defenseRepoId);
				put("attack_act", battleFactor.idx);
				put("defense_act", DODGE.idx);
				put("damage", 0);
			}
		};
	}


	public static HashMap<String, Object> useSkillLog(Integer turn, Long attackRepoId,
		Long defenseRepoId, Integer skillDmg) {
		return new HashMap<>() {
			{
				put("turn", turn);
				put("attacker", attackRepoId);
				put("defender", defenseRepoId);
				put("attack_act", SKILL.idx);
				put("defense_act", ATTACKED.idx);
				put("damage", skillDmg);
			}
		};
	}


	public static Integer getResultPoint(RepomonStatusEntity myRepomon,
		RepomonStatusEntity yourRepomon) {
		// Elo 계산 때 사용하는 스탯 차이
		Integer statusGap = createGap(myRepomon, yourRepomon);

		return (int) Math.round((1 - (1 / (1 + Math.pow(10,
			((double) (yourRepomon.getRating() - myRepomon.getRating() - statusGap) / 400)))))
			* maxRating);

	}

}
