package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.domain.repo.app.UserCardDetail;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserCardResponseDto {

	private final Long totalCommitCount;
	private final Long totalCodeLineCount;
	private final List<String> languages;
	private final Long avgContribution;
	// private final Long totalMergeCount;
	// private final Long totalIssueCount;


	public static UserCardResponseDto fromEntity(UserCardDetail userCardDetail) {

		return UserCardResponseDto.builder()
			.totalCommitCount(userCardDetail.getTotalCommitCount())
			.totalCodeLineCount(userCardDetail.getTotalCodeLineCount())
			.languages(userCardDetail.getLanguages())
			.avgContribution(userCardDetail.getAvgContribution())
			.build();
	}

}
