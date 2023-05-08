package com.repomon.rocketdan.domain.repo.entity;


import com.repomon.rocketdan.domain.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "personal_language")
public class PersonalLanguageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_language_id")
    private Long personalLanguageId;

    private String languageCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repo_id")
    private RepoEntity repoEntity;


    public static PersonalLanguageEntity of(String languageCode, RepoEntity repoEntity) {
        return PersonalLanguageEntity.builder()
                .languageCode()
                .repoEntity(repoEntity)
                .build();
    }

    private static Object builder() {
    }
}
