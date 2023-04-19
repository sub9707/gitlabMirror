package com.repomon.rocketdan.domain.repo.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PersonalRepoCardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_repo_card_id")
	private Long personalRepoCardId;

	private String personalRepoCardUrl;
	private Float personalRepoCardAttribute;
	private Integer personalRepoCardLine;

	private Long repoId;

}
