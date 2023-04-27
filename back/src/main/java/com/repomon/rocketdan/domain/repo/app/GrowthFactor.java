package com.repomon.rocketdan.domain.repo.app;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GrowthFactor {
    COMMIT(1), MERGE(2), ISSUE(3), REVIEW(4), SECURITY(5), EFFICIENCY(6);

    Integer idx;

    public static GrowthFactor idxToName(int idx){
        return Arrays.stream(GrowthFactor.values())
            .filter(growthFactor -> growthFactor.idx == idx)
            .findFirst().orElseThrow();
    }
}
