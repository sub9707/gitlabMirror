package com.repomon.rocketdan.domain.repo.entity;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PersonalLanguageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "personal_language_id")
	private Long personalLanguageId;

	private String languageCode;
	private Long personalCardId;
}
