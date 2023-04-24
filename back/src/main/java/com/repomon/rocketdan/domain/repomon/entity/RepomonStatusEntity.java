package com.repomon.rocketdan.domain.repomon.entity;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RepomonStatusEntity extends RepoEntity {

	private Integer rating;
	private Integer statPoint;
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
}
