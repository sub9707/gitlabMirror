package com.repomon.rocketdan.domain.repo.dto.response;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RepoContributeResponseDto {

    private int totalCommitCount;
    private Map<String, Integer> committers;
    private String mvp;
    private String repoOwner;
}
