package com.repomon.rocketdan.domain.repo.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter @Builder
@RedisHash("repo-contribute")
@AllArgsConstructor
public class RepoContributeResponseDto {

    @Id @JsonIgnore
    private Long id;
    @Indexed
    private String repoOwner;
    private int totalCommitCount;
    private Map<String, Integer> committers;
    private String mvp;


    public static RepoContributeResponseDto of(int totalCommitCount,
        Map<String, Integer> committers, String mvp, String repoOwner) {
        return RepoContributeResponseDto.builder()
            .totalCommitCount(totalCommitCount)
            .committers(committers)
            .mvp(mvp)
            .repoOwner(repoOwner)
            .build();
    }
}


