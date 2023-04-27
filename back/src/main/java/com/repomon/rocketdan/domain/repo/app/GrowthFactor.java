package com.repomon.rocketdan.domain.repo.app;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrowthFactor {
    COMMIT(1, 10L), MERGE(2, 5L), ISSUE(3, 2L), REVIEW(4, 0L), SECURITY(5, 0L), EFFICIENCY(6, 0L);

    Integer idx;
    Long exp;

    public static GrowthFactor idxToEnum(int idx){
        return Arrays.stream(GrowthFactor.values())
            .filter(growthFactor -> growthFactor.idx == idx)
            .findFirst().orElseThrow();
    }
}
