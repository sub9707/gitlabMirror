package com.repomon.rocketdan.domain.repo.app;


import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCardDetail {
	private Long totalCommitCount;
	private Long totalCodeLineCount;
	private List<String> languages;
	private Long avgContribution;
	// private final Long totalMergeCount;
	// private final Long totalIssueCount;
}
