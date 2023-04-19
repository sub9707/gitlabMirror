package com.repomon.rocketdan.domain.user.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLanguageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_language_id")
	private Long userLanguageId;

	private String languageCode;

	private Long userId;

}
