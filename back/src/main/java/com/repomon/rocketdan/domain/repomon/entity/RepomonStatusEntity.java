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
	
	// 아래에 스탯 추가 예정
}
