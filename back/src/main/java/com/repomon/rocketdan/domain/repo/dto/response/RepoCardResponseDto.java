package com.repomon.rocketdan.domain.repo.dto.response;

import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTag;


    @AllArgsConstructor
    @Getter
    @ToString
    @Builder
    public class RepoCardResponseDto {
        /**
         * -------레포카드 detail------
         * 레포 이름 repoName
         * 레포 기간 repoStart repoEnd
         * 레포 포크, 스타  starCnt forkCnt
         * 레포 description
         * 레포 언어 languages
         * -----------그래프 ----------
         * 커밋 commits
         * 머지 merges
         * 이슈 issues
         * 리뷰 reviews
         * 보안성 security
         * 효율성 efficiency
         * --------------------------
         * 전체 커밋 totalcommit
         * 전체 코드 totalcode
         * 레포몬 repomonId
         * 전체 경험치 repoExp
         * 컨트리뷰터 수 contributers
         */
        private String repoName;
        private Long repomonId;
        private String repoDescription;
        private Long repoExp;
        private int starCnt;
        private int forkCnt;
        private LocalDateTime repoStart;
        private LocalDateTime repoEnd;
        private List<String> languages;
        private int contributers;
        private Long commits;
        private Long issues;
        private Long merges;
        private Long reviews;
        private int security;
        private int efficiency;
        private Long totalcommit;
        private Long totalcode;

        public static RepoCardResponseDto fromEntityAndGHRepository(RepoEntity repoEntity, GHRepository ghRepository) {
            try {
                Map<String, Long> languageMap = ghRepository.listLanguages();

                List<String> languages = new ArrayList<>();

                languages.addAll(languageMap.keySet());


                return RepoCardResponseDto.builder()
                        .repoName(repoEntity.getRepoName())
                        .repomonId(repoEntity.getRepomon().getRepomonId())
                        .repoDescription(ghRepository.getDescription())
                        .repoExp(repoEntity.getRepoExp())
                        .starCnt(ghRepository.getStargazersCount())
                        .forkCnt(ghRepository.getForksCount())
                        .repoStart(repoEntity.getRepoStart())
                        .repoEnd(repoEntity.getRepoEnd())
                        .languages(languages)
                        .repoExp(repoEntity.getRepoExp())
                        .contributers(6)
                        .commits(60L)
                        .issues(80L)
                        .merges(70L)
                        .reviews(60L)
                        .efficiency(80)
                        .security(70)
                        .totalcommit(17870L)
                        .totalcode(1217870L)
                        .build();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
