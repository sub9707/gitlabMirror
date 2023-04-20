package com.repomon.rocketdan.domain.repo.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PersonalRepoCardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_repo_card_id")
	private Long personalRepoCardId;

	private Float personalRepoCardAttribute;
	private Integer personalRepoCardLine;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "repo_id")
	private RepoEntity repo;

	@OneToMany(mappedBy = "personalRepoCard")
	private List<PersonalLanguageEntity> personalLanguageList = new ArrayList<>();

}
