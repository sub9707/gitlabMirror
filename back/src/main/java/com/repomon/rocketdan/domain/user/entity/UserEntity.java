package com.repomon.rocketdan.domain.user.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	private String userName;

	@OneToOne
	@JoinColumn(name = "represent_repo_id")
	private ActiveRepoEntity representRepo;


	public void updateRepresentRepo(ActiveRepoEntity repo) {
		this.representRepo = repo;
	}

}
