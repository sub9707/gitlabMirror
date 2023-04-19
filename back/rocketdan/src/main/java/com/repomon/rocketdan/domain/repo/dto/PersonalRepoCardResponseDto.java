package com.repomon.rocketdan.domain.repo.dto;


import com.repomon.rocketdan.domain.repo.entity.PersonalRepoCardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalRepoCardResponseDto {

	public static PersonalRepoCardResponseDto fromEntity(PersonalRepoCardEntity personalRepoCard) {
		return new PersonalRepoCardResponseDto();
	}

	public static List<PersonalRepoCardResponseDto> fromEntityList(List<PersonalRepoCardEntity> personalRepoCardList) {
		List<PersonalRepoCardResponseDto> result = new ArrayList<>();
		for (PersonalRepoCardEntity personalRepoCard : personalRepoCardList) {
			PersonalRepoCardResponseDto personalRepoCardResponseDto = PersonalRepoCardResponseDto.fromEntity(personalRepoCard);
			result.add(personalRepoCardResponseDto);
		}
		return result;
	}
}
