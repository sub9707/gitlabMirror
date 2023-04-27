package com.repomon.rocketdan.domain.repomon.entity;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "repomon_status")
public class RepomonStatusEntity extends RepoEntity {

	private Integer winCnt;
	private Integer loseCnt;
	private Integer startAtk;
	private Integer startDodge;
	private Integer startDef;
	private Integer startCritical;
	private Integer startHit;
	private Integer atkPoint;
	private Integer dodgePoint;
	private Integer defPoint;
	private Integer criticalPoint;
	private Integer hitPoint;


	public void updateStatus(int atk, int dodge, int def, int critical, int hit) {
		this.atkPoint += atk;
		this.dodgePoint += dodge;
		this.defPoint += def;
		this.criticalPoint += critical;
		this.hitPoint += hit;
	}


	public void setStartStatus(int atk, int dodge, int def, int critical, int hit) {
		this.atkPoint = atk;
		this.dodgePoint = dodge;
		this.defPoint = def;
		this.criticalPoint = critical;
		this.hitPoint = hit;
	}


	public void updateWinCnt() {
		this.winCnt++;
	}


	public void updateLoseCnt() {
		this.loseCnt++;
	}

}
