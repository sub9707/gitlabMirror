package com.repomon.rocketdan.domain.user.entity;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	private String userName;
	private Long userViews;

	// 유저 정보 추가 예정

	@OneToMany(mappedBy = "user")
	private List<ActiveRepoEntity> activeRepoList = new ArrayList<>();

}
