package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.domain.user.entity.UserEntity;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserRankResponseDto {

	private Long userId;
	private Long totalExp;
	private String username;
	private Long activeRepoCount;


	public static UserRankResponseDto fromEntity(UserEntity user, Long activeRepoCount) {

		return UserRankResponseDto.builder()
			.userId(user.getUserId())
			.username(user.getUserName())
			.totalExp(user.getTotalExp())
			.activeRepoCount(activeRepoCount)
			.build();
	}

}
