package com.repomon.rocketdan.domain.repomon.dto.request;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;


@Data
public class RepomonStartStatusRequestDto {

	private Long repoId;
	private Long repomonId;
	private String repomonNickname;

	@Max(value = 10)
	@Positive
	private Integer startAtk;

	@Max(value = 10)
	@Positive
	private Integer startDodge;

	@Max(value = 10)
	@Positive
	private Integer startDef;

	@Max(value = 10)
	@Positive
	private Integer startCritical;

	@Max(value = 10)
	@Positive
	private Integer startHit;


	public static boolean isValid(RepomonStartStatusRequestDto requestDto) {
		int totalPoint = requestDto.getStartAtk() + requestDto.getStartCritical() + requestDto.getStartDef() +
			requestDto.getStartDodge() + requestDto.getStartHit();

		return (totalPoint > 30);
	}

}
