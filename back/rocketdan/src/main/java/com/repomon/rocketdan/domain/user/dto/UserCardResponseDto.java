package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.domain.user.entity.UserCardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCardResponseDto {

	public static UserCardResponseDto fromEntity(UserCardEntity userCard) {
		return new UserCardResponseDto();
	}


	public static List<UserCardResponseDto> fromEntityList(List<UserCardEntity> userCardList) {
		List<UserCardResponseDto> result = new ArrayList<>();
		for (UserCardEntity userCard : userCardList) {
			UserCardResponseDto userCardResponseDto = UserCardResponseDto.fromEntity(userCard);
			result.add(userCardResponseDto);
		}
		return result;
	}
}
