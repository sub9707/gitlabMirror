package com.repomon.rocketdan.domain.repo.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RepoConventionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repo_convention_id")
	private Long repoConventionId;

	private String repoConventionType;
	private String repoConventionDes;

	private Long repoId;
}
