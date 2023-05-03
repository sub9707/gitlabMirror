package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.domain.repo.app.RepoListItem;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserCardResponseDto {

	private final Long userId;
	private final String username;
	private final String nickname;
	private final String avatarUrl;
	private Long totalExp;
	private RepoListItem representRepo;


	public static UserCardResponseDto fromEntity() {

		return UserCardResponseDto.builder()
			.build();
	}
}
