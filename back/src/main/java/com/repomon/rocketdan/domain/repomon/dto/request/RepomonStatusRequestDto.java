package com.repomon.rocketdan.domain.repomon.dto.request;


import lombok.Data;

import javax.validation.constraints.Positive;


@Data
public class RepomonStatusRequestDto {

	private Long repoId;

	@Positive
	private Integer atkPoint = 0;
	@Positive
	private Integer dodgePoint = 0;
	@Positive
	private Integer defPoint = 0;
	@Positive
	private Integer criticalPoint = 0;
	@Positive
	private Integer hitPoint = 0;

}
