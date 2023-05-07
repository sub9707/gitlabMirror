package com.repomon.rocketdan.domain.user.dto;


import com.repomon.rocketdan.common.utils.GHUtils;
import com.repomon.rocketdan.domain.repo.app.UserCardDetail;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.exception.CustomException;
import com.repomon.rocketdan.exception.ErrorCode;
import lombok.*;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserCardResponseDto {

	private Long MyrepomonId;
	private Long MyrepoExp;
	private int MyrepomonTier;
	private String MyrepoName;

	private final Long totalExp;

	private String userName;
	private String avatarUrl;
	private String introduce;
	private Integer repoCount;

	private final Long totalCommitCount;
	private final Long totalCodeLineCount;
	private final List<String> languages;
	private final Long avgContribution;
	private final Long totalMergeCount;
	private final Long totalReviewCount;
	private final Long totalIssueCount;
	private final Long starCount;
	private final Long forkCount;


	public static UserCardResponseDto fromEntity(UserCardDetail userCardDetail, UserEntity user) throws IOException {

		RepoRepository repoRepository = null;

		GitHub gitHub = null;
		GHUtils ghUtils = new GHUtils();
		Map<String, String> userInfo = ghUtils.getUser(user.getUserName());
		GHUser githubUser = gitHub.getUser(user.getUserName());

		RepoEntity repoEntity = repoRepository.findById(user.getRepresentRepo().get().getActiveRepoId()).orElseThrow(() -> {
			throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
		});

		return UserCardResponseDto.builder()
			.userName(userInfo.get("nickname"))
			.avatarUrl(userInfo.get("avatarUrl"))
			.totalExp(user.getTotalExp())
			.introduce(githubUser.getBio())
			.repoCount(userCardDetail.getRepoCount())
				.MyrepoExp(repoEntity.getRepoExp())
				.MyrepomonId(repoEntity.getRepoId())
				.MyrepoName(repoEntity.getRepoName())
				.MyrepomonTier(repoEntity.getRepomon().getRepomonTier())
			.totalCommitCount(userCardDetail.getTotalCommitCount())
			.totalCodeLineCount(userCardDetail.getTotalCodeLineCount())
			.languages(userCardDetail.getLanguages())
			.avgContribution(userCardDetail.getAvgContribution())
			.totalMergeCount(userCardDetail.getTotalMergeCount())
			.totalReviewCount(userCardDetail.getTotalReviewCount())
			.totalIssueCount(userCardDetail.getTotalIssueCount())
			.starCount(userCardDetail.getStarCount())
			.forkCount(userCardDetail.getForkCount())
			.build();
	}

}
