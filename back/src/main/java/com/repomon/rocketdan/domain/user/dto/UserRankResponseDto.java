package com.repomon.rocketdan.domain.user.dto;


import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
public class UserRankResponseDto {

	private Long userId;
	private Long totalExp;
	private String username;
	private Long repoCount;



}
