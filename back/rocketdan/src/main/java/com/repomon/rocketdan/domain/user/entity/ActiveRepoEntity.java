package com.repomon.rocketdan.domain.user.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ActiveRepoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "active_repo_id")
	private Long activeRepoId;

	private Boolean isActive;

	private Long userId;
	private Long repoId;
}
