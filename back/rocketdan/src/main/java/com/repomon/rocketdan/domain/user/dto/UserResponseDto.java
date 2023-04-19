package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.domain.user.entity.UserEntity;
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
public class UserResponseDto {

	public static UserResponseDto fromEntity(UserEntity user) {
		return new UserResponseDto();
	}

	public static List<UserResponseDto> fromEntityList(List<UserEntity> userList) {
		List<UserResponseDto> result = new ArrayList<>();

		for (UserEntity user : userList) {
			UserResponseDto userResponseDto = UserResponseDto.fromEntity(user);
			result.add(userResponseDto);
		}
		return result;
	}
}
