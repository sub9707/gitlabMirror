package com.repomon.rocketdan.domain.repo.app;


import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCardDetail {

	private Long myrepomonId;
	private Long myrepoExp;
	private int myrepomonTier;
	private String myrepoName;

	private Long totalExp;

	private String userName;
	private String avatarUrl;
	private String introduce;
	private Integer repoCount;

	private Long totalCommitCount;
	private Long totalCodeLineCount;
	private List<String> languages;
	private Long avgContribution;
	private Long totalMergeCount;
	private Long totalReviewCount;
	private Long totalIssueCount;
	private Long starCount;
	private Long forkCount;
}
