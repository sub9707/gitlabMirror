package com.repomon.rocketdan.domain.repo.entity;


import com.repomon.rocketdan.common.entity.CommonEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RepoEntity extends CommonEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repo_id")
	private Long repoId;

	private String repoName;
	private String repoOwner;
	private String repomonNickname;
	private Long repoExp;
	private Integer repomonTier;
	private String repoKey;
	private LocalDateTime repoStart;
	private LocalDateTime repoEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="repomon_id")
	private RepomonEntity repomon;

	@OneToMany(mappedBy = "repo")
	private List<RepoConventionEntity> repoConventionList = new ArrayList<>();

}
