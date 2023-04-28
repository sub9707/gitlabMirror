package com.repomon.rocketdan.domain.repo.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.repomon.rocketdan.domain.repo.entity.RepoConventionEntity;
import java.util.stream.Collectors;
import javax.persistence.Id;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;


@Getter
@RedisHash(value = "repo-convention")
@AllArgsConstructor
public class RepoConventionResponseDto {
	@Id @JsonIgnore
	private Long id;
	@Indexed
	private String repoOwner;
	private List<ConventionInfo> conventions;
	private int totalCnt;
	private int collectCnt;

	public static RepoConventionResponseDto fronEntities(String repoOwner, List<RepoConventionEntity> conventions,
		int totalCnt, int collectCnt) {
		List<ConventionInfo> collect = conventions.stream().map(ConventionInfo::of)
			.collect(Collectors.toList());

		return new RepoConventionResponseDto(null, repoOwner, collect, totalCnt, collectCnt);
	}

	@AllArgsConstructor
	private static class ConventionInfo{
		private String prefix;
		private String description;

		private static ConventionInfo of(RepoConventionEntity conventionEntity){
			return new ConventionInfo(conventionEntity.getRepoConventionType(), conventionEntity.getRepoConventionDes());
		}
	}
}




