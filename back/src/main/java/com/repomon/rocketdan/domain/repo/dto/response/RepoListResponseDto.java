package com.repomon.rocketdan.domain.repo.dto.response;


import com.repomon.rocketdan.domain.repo.app.RepoDetail;
import com.repomon.rocketdan.domain.repo.app.RepoListItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RepoListResponseDto {

    private int totalPages;
    private long totalElements;
    private List<RepoListItem> repoListItems;


    public static RepoListResponseDto fromDetails(List<RepoDetail> repoDetails, long totalElements, int totalPages) {
        List<RepoListItem> collect = repoDetails.stream().map(RepoListItem::convertFromDetail)
            .collect(Collectors.toList());
        return new RepoListResponseDto(totalPages, totalElements, collect);
    }


    public RepoListItem getRepoItem(RepoDetail repoDetail) {
        return RepoListItem.convertFromDetail(repoDetail);
    }

}
