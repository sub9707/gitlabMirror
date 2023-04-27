package com.repomon.rocketdan.domain.repo.dto;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;
import org.kohsuke.github.PagedIterable;


@AllArgsConstructor
@Getter
@ToString
@Builder
public class RepoResponseDto {

	private String repoName;
	private Long repomonId;
	private String repomonName;
	private Long repoExp;
	private int starCnt;
	private int forkCnt;
	private LocalDateTime repoStart;
	private LocalDateTime repoEnd;
	private List<String> languages;
	private List<String> tags;

	public static RepoResponseDto fromEntityAndGHRepository(RepoEntity repoEntity, GHRepository ghRepository) {
		Map<String, Long> languageMap = null;
		List<GHTag> ghTags = null;
		try {
			languageMap = ghRepository.listLanguages();
			ghTags = ghRepository.listTags().toList();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		List<String> languages = new ArrayList<>();
		List<String> tags = new ArrayList<>();

		languages.addAll(languageMap.keySet());
		tags.addAll(ghTags.stream().map(GHTag::getName).collect(Collectors.toList()));

		return RepoResponseDto.builder()
			.repoName(repoEntity.getRepoName())
			.repomonId(repoEntity.getRepomon().getRepomonId())
			.repomonName(repoEntity.getRepomonNickname())
			.repoExp(repoEntity.getRepoExp())
			.starCnt(ghRepository.getStargazersCount())
			.forkCnt(ghRepository.getForksCount())
			.repoStart(repoEntity.getRepoStart())
			.repoEnd(repoEntity.getRepoEnd())
			.languages(languages)
			.tags(tags)
			.build();
	}
}
