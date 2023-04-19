package com.repomon.rocketdan.domain.repo.entity;


import com.repomon.rocketdan.common.entity.CommonEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RepoEntity extends CommonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repo_id")
	private Long repoId;

	private String repoName;
	private String repoOwner;
	private String repomonNickname;
	private Long repoExp;
	private Integer repomonTier;
	private String repoKey;
	private LocalDateTime repoStart;
	private LocalDateTime repoEnd;

	private Long repomonId;
}
