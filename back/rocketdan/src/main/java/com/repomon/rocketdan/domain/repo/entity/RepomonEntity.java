package com.repomon.rocketdan.domain.repo.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RepomonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repomon_id")
	private Long repomonId;

	private String repomonName;
	private Integer repomonSkill;

}
