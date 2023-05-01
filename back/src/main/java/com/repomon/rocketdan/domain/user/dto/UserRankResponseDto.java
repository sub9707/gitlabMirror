package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.domain.user.entity.UserCardEntity;
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


	public static UserRankResponseDto fromEntity(UserCardEntity userCard) {
		return UserRankResponseDto.builder()
			.userId(userCard.getUser().getUserId())
			.totalExp(userCard.getTotalExp())
			.username(userCard.getUser().getUserName())
			.build();
	}


	public static List<UserRankResponseDto> fromEntityList(List<UserCardEntity> userCardList) {
		List<UserRankResponseDto> result = new ArrayList<>();
		for (UserCardEntity usersCard : userCardList) {
			UserRankResponseDto repoRankResponseDto = UserRankResponseDto.fromEntity(usersCard);
			result.add(repoRankResponseDto);
		}
		return result;
	}

}
