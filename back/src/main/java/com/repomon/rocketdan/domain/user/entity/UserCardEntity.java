package com.repomon.rocketdan.domain.user.entity;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_card")
public class UserCardEntity extends UserEntity {

    private Long totalExp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repo_id")
    private RepoEntity repo;

    @OneToMany(mappedBy = "userCard")
    private List<UserLanguageEntity> userLanguageList = new ArrayList<>();

}
