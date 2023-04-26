package com.repomon.rocketdan.domain.repomon.dto;


import lombok.Data;


@Data
public class RepomonStatusRequestDto {
    private Long repoId;
    private String repomonNickname;

    private Integer startAtk;
    private Integer startDodge;
    private Integer startDef;
    private Integer startCritical;
    private Integer startHit;

    private Integer atkPoint;
    private Integer dodgePoint;
    private Integer defPoint;
    private Integer criticalPoint;
    private Integer hitPoint;
}
