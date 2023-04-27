package com.repomon.rocketdan.domain.repo.dto.response;

import com.repomon.rocketdan.domain.repo.app.GrowthFactor;
import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepoResearchResponseDto {

    private int rank;
    private Long repoExp;
    private Long totalGetExp;
    private Map<String, Long> growthFactor;
    private List<HistoryInfo> histories;

    public static RepoResearchResponseDto fromHistoryAndRank(
        List<RepoHistoryEntity> historyEntityList, int rank, Long repoExp) {

        List<HistoryInfo> histories = historyEntityList.stream()
            .map(HistoryInfo::fromEntity)
            .collect(Collectors.toList());

        Long totalGetExp = 0L;
        Map<String, Long> growthFactor = new HashMap<>();
        for(HistoryInfo historyInfo : histories){
            totalGetExp += historyInfo.exp;

            Long nowExp = 0L;
            String typeName = GrowthFactor.idxToName(historyInfo.type).name();
            if(growthFactor.containsKey(typeName)){
                nowExp = growthFactor.get(typeName);
            }

            growthFactor.put(typeName, nowExp + historyInfo.exp);
        }

        return RepoResearchResponseDto.builder()
            .rank(rank)
            .repoExp(repoExp)
            .totalGetExp(totalGetExp)
            .growthFactor(growthFactor)
            .histories(histories)
            .build();
    }

    @AllArgsConstructor
    private static class HistoryInfo{

        private Long exp;
        private Integer type;
        private LocalDateTime createdAt;

        public static HistoryInfo fromEntity(RepoHistoryEntity historyEntity){
            return new HistoryInfo(historyEntity.getRepoHistoryExp(), historyEntity.getRepoHistoryType(), historyEntity.getCreatedAt());
        }
    }
}
