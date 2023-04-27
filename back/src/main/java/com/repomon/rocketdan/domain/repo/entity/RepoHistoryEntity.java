package com.repomon.rocketdan.domain.repo.entity;


import com.repomon.rocketdan.common.entity.CommonEntity;
import com.repomon.rocketdan.domain.repo.app.GrowthFactor;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.experimental.SuperBuilder;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHIssue;
import org.kohsuke.github.GHPullRequest;


@Entity
@Getter
@Builder
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
    private LocalDate workedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repo_id")
    private RepoEntity repo;

    public void updateExp(Long exp){
        this.repoHistoryExp += exp;
    }
    public static RepoHistoryEntity ofCommit(LocalDate commitDate, RepoEntity repoEntity){
        return RepoHistoryEntity.builder()
            .repoHistoryExp(10L)
            .repoHistoryType(GrowthFactor.COMMIT.getIdx())
            .repo(repoEntity)
            .workedAt(commitDate)
            .build();
    }

    public static RepoHistoryEntity ofPullRequest(LocalDate prDate, RepoEntity repoEntity) {
        return RepoHistoryEntity.builder()
            .repoHistoryExp(5L)
            .repoHistoryType(GrowthFactor.MERGE.getIdx())
            .repo(repoEntity)
            .workedAt(prDate)
            .build();
    }

    public static RepoHistoryEntity ofIssue(LocalDate issueClosedDate, RepoEntity repoEntity) {
        return RepoHistoryEntity.builder()
            .repoHistoryExp(2L)
            .repoHistoryType(GrowthFactor.ISSUE.getIdx())
            .repo(repoEntity)
            .workedAt(issueClosedDate)
            .build();
    }
}
