package com.repomon.rocketdan.domain.user.dto.response;


import com.repomon.rocketdan.domain.repo.app.RepoListItem;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import lombok.*;

import java.util.Map;


@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserResponseDto {

	private final Long userId;
	private final String username;
	private final String nickname;
	private final String avatarUrl;
	private Long totalExp;
	private RepoListItem representRepo;
	private Integer userRank;


	public static UserResponseDto fromEntity(UserEntity user, Map<String, String> userInfo) {

		return UserResponseDto.builder()
			.userId(user.getUserId())
			.username(userInfo.get("username"))
			.avatarUrl(userInfo.get("avatarUrl"))
			.nickname(userInfo.get("nickname"))
			.build();
	}

}
