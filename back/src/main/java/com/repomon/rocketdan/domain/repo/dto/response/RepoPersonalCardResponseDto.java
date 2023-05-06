package com.repomon.rocketdan.domain.repo.dto.response;

import com.repomon.rocketdan.domain.repo.app.GrowthFactor;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.kohsuke.github.GHRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Getter
@ToString
@Builder
public class RepoPersonalCardResponseDto {
    /**
     * -------레포 개인 카드 detail------
     * 레포 이름 repoName
     * 레포 기간 repoStart repoEnd
     * 레포 언어 languages
     * -----------그래프 ----------
     * 커밋 commits
     * 머지 merges
     * 이슈 issues
     * 리뷰 reviews
     * 레포 포크, 스타  starCnt forkCnt
     *-----------그래프 ----------
     * 커밋 mycommits
     * 머지 mymerges
     * 이슈 myissues
     * 리뷰 myreviews
     * 전체 커밋 mytotalcommit
     * 전체 코드 mytotalcode
     * 기여도 my contribution
     * --------------------------
     * 레포몬 repomonId
     * 전체 경험치 repoExp
     * 컨트리뷰터 수 contributers >> 레디스
     * --------------------------
     * 깃허브 아이디 gitname
     * 깃허브 사진 git img
     */
    private Long repomonId;
    private Long repoExp;
    private int repomonTier;
    private int contribution;

    private String repoName;
    private int contributers;
    private LocalDateTime repoStart;
    private LocalDateTime repoEnd;
    private List<String> languages;

    private Long totalcommit;
    private Long totalcode;
    private int mytotalcommit;
    private Long mytotalcode;
    private int conventionrate;

    private int starCnt;
    private int forkCnt;
    private Long commits;
    private Long issues;
    private Long merges;
    private Long reviews;

    private int myissues;
    private int mymerges;
    private int myreviews;

    private String userName;
    private String avatarUrl;


    public static RepoPersonalCardResponseDto fromEntityAndGHRepository(RepoEntity repoEntity, GHRepository ghRepository, List<RepoHistoryEntity> historyEntityList, Integer contributers, Map<String, String> userInfo, RepoContributeResponseDto contributeResponse,Integer myissue, Long mytotalcode, List<Integer> mymerges) {
        try {
            Map<String, Long> languageMap = ghRepository.listLanguages();

            List<String> languages = new ArrayList<>();

            languages.addAll(languageMap.keySet());

            Long commitsExp = 0L;
            Long mergesExp = 0L;
            Long issuesExp = 0L;
            Long reviewsExp = 0L;

            for (RepoHistoryEntity repoHistory : historyEntityList) {
                Integer repoHistoryType = repoHistory.getRepoHistoryType();
                GrowthFactor growthFactor = GrowthFactor.idxToEnum(repoHistoryType);

                switch(repoHistoryType){
                    case 1:
                        commitsExp += growthFactor.getExp();
                        break;
                    case 2:
                        mergesExp += growthFactor.getExp();
                        break;
                    case 3:
                        issuesExp += growthFactor.getExp();
                        break;
                    case 4:
                        reviewsExp += growthFactor.getExp();
                        break;
                }
            }

//            int mycontribution = contributeResponse.getCommitters().get(userInfo.get("username"))/contributeResponse.getTotalCommitCount()*100;

            return RepoPersonalCardResponseDto.builder()
                    .repomonId(repoEntity.getRepomon().getRepomonId())
                    .repomonTier(repoEntity.getRepomon().getRepomonTier())
                    .contribution(60)
                    .repoExp(repoEntity.getRepoExp())

                    .repoName(repoEntity.getRepoName())
                    .contributers(contributers)
                    .repoStart(repoEntity.getRepoStart())
                    .repoEnd(repoEntity.getRepoEnd())
                    .languages(languages)

                    .starCnt(ghRepository.getStargazersCount())
                    .forkCnt(ghRepository.getForksCount())
                    .commits(commitsExp)
                    .issues(issuesExp)
                    .merges(mergesExp)
                    .reviews(reviewsExp)

                    .myissues(myissue)
                    .mymerges(mymerges.get(0))
                    .myreviews(mymerges.get(1))

                    .totalcommit(60L)
                    .totalcode(contributeResponse.getTotalLineCount())
                    .mytotalcommit(contributeResponse.getCommitters().get(userInfo.get("username")))
                    .mytotalcode(mytotalcode)
                    .conventionrate(60)

                    .userName(userInfo.get("nickname"))
                    .avatarUrl(userInfo.get("avatarUrl"))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
