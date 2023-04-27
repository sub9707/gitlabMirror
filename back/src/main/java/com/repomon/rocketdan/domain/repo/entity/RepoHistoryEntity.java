package com.repomon.rocketdan.domain.repo.entity;


import com.repomon.rocketdan.common.entity.CommonEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "repo_history")
public class RepoHistoryEntity extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repo_history_id")
    private Long repoHistoryId;

    private Long repoHistoryExp;
    private Integer repoHistoryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repo_id")
    private RepoEntity repo;

}
