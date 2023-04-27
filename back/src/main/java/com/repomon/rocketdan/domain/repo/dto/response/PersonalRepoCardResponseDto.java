package com.repomon.rocketdan.domain.repo.dto.response;


import com.repomon.rocketdan.domain.repo.entity.PersonalRepoCardEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;



@AllArgsConstructor
@Getter
@ToString
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
