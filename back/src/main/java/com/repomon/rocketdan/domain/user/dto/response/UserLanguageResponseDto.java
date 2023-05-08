package com.repomon.rocketdan.domain.user.dto.response;


import com.repomon.rocketdan.domain.user.entity.UserLanguageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;




@AllArgsConstructor
@Getter
@ToString
@Builder
public class UserLanguageResponseDto {

	public static UserLanguageResponseDto fromEntity(UserLanguageEntity userLanguage) {
		return new UserLanguageResponseDto();
	}

	public static List<UserLanguageResponseDto> fromEntityList(List<UserLanguageEntity> userLanguageList) {
		List<UserLanguageResponseDto> result = new ArrayList<>();
		for (UserLanguageEntity userLanguage : userLanguageList) {
			UserLanguageResponseDto userLanguageResponseDto = UserLanguageResponseDto.fromEntity(userLanguage);
			result.add(userLanguageResponseDto);
		}
		return result;
	}
}
