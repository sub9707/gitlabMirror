package com.repomon.rocketdan.domain.repo.dto;

import com.repomon.rocketdan.domain.repo.app.RepoDetail;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepoListResponseDto {

    private List<RepoListItem> repoListItems;
    public static RepoListResponseDto fromDetails(List<RepoDetail> repoDetails) {
        List<RepoListItem> collect = repoDetails.stream().map(RepoListItem::convertFromDetail)
            .collect(Collectors.toList());
        return new RepoListResponseDto(collect);
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RepoListItem{
        private Long repomonId;
        private String repoName;
        private String repomonName;
        private String repoDescription;
        private Long repoExp;
//        private Integer repoRating;

        public static RepoListItem convertFromDetail(RepoDetail repoDetail){
            RepoEntity repo = repoDetail.getRepoEntity();
            RepomonEntity repomon = repo.getRepomon();

            return RepoListItem.builder()
                .repomonId(repomon == null ? null : repomon.getRepomonId())
                .repoName(repo.getRepoName())
                .repomonName(repo.getRepomonNickname())
                .repoDescription(repoDetail.getDescription())
                .repoExp(repo.getRepoExp())
//                .repoRating(repo.getRating())
                .build();
        }
    }
}
