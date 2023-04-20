package com.repomon.rocketdan.domain.user.entity;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCardEntity extends UserEntity {

	private Long totalExp;

	private Long repoId;
}
