package com.repomon.rocketdan.domain.repo.entity;


import com.repomon.rocketdan.common.entity.CommonEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RepoHistoryEntity extends CommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repo_history_id")
	private Long repoHistoryId;

	private Long repoHistoryExp;
	private Integer repoHistoryType;

	private Long repoId;

}
