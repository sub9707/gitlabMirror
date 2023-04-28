package com.repomon.rocketdan.domain.repo.dto.request;


import com.repomon.rocketdan.domain.repo.entity.RepoConventionEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;


@Data
public class RepoConventionRequestDto {

    List<ConventionItem> conventions;

    public List<RepoConventionEntity> toEntities(RepoEntity repoEntity) {
        return conventions.stream()
            .map(item -> new RepoConventionEntity(null, item.getType(), item.getDesc(), repoEntity))
            .collect(Collectors.toList());
    }

    @Data
    public static class ConventionItem{
        private String type;
        private String desc;
    }
}
