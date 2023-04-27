package com.repomon.rocketdan.domain.repo.dto.response;

import com.repomon.rocketdan.domain.repomon.app.DefaultStatus;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepoBattleResponseDto {

    private int rating;
    private int rank;
    private int winCnt;
    private int loseCnt;
    private RepomonStatus status;

    public static RepoBattleResponseDto fromStatusEntity(RepomonStatusEntity statusEntity,
        int rank) {

        RepomonStatus status = new RepomonStatus(0,0,0,0,0,0);

        return RepoBattleResponseDto.builder()
            .rating(statusEntity.getRating())
            .rank(rank)
            .winCnt(statusEntity.getWinCnt())
            .loseCnt(statusEntity.getLoseCnt())
            .status(status)
            .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    private static class RepomonStatus{
        private int hp;
        private int atk;
        private int dodge;
        private int def;
        private int critial;
        private int hit;


    }
}