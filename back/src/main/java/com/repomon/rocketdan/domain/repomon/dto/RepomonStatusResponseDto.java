package com.repomon.rocketdan.domain.repomon.dto;


import com.repomon.rocketdan.domain.repo.dto.RepomonResponseDto;
import com.repomon.rocketdan.domain.repomon.app.DefaultStatus;
import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class RepomonStatusResponseDto {

	private String repoName;
	private String repomonNickname;
	private Integer repomonTier;
	private Integer rating;
	private Integer statPoint;
	private Integer winCnt;
	private Integer loseCnt;

	// 내부 공식으로 계산한 수치 (디폴트값 + 각 포인트 반영 %) 근데 프론트에서 가지고 있어야 하지 않을까싶음.
	// 최초에 주사위 굴린 수치도 여기에 반영됨
	@Builder.Default
	private Integer atk = 0;
	@Builder.Default
	private Float dodge = 0f;
	@Builder.Default
	private Float def = 0f;
	@Builder.Default
	private Float critical = 0f;
	@Builder.Default
	private Float hit = 0f;

	// 현재까지 찍은 스탯값
	private Integer atkPoint;
	private Integer dodgePoint;
	private Integer defPoint;
	private Integer criticalPoint;
	private Integer hitPoint;

	// 체력
	private Integer hp;


	private RepomonResponseDto repomon;

	public static RepomonStatusResponseDto fromEntity(RepomonStatusEntity repomonStatus) {
		return RepomonStatusResponseDto.builder()
				.repoName(repomonStatus.getRepoName())
				.repomonNickname(repomonStatus.getRepomonNickname())
				.repomonTier(repomonStatus.getRepomonTier())
				.rating(repomonStatus.getRating())
				.statPoint(repomonStatus.getStatPoint())
				.winCnt(repomonStatus.getWinCnt())
				.loseCnt(repomonStatus.getLoseCnt())
				.atkPoint(repomonStatus.getAtkPoint())
				.dodgePoint(repomonStatus.getDodgePoint())
				.defPoint(repomonStatus.getDefPoint())
				.criticalPoint(repomonStatus.getCriticalPoint())
				.hitPoint(repomonStatus.getHitPoint())
				.atk(((repomonStatus.getStartAtk()+ repomonStatus.getAtkPoint())*DefaultStatus.atkValue) + DefaultStatus.defaultAtk)
				.dodge(((repomonStatus.getStartDodge()+repomonStatus.getDodgePoint())*DefaultStatus.dodgeValue) + DefaultStatus.defaultDodge)
				.def(((repomonStatus.getStartDef()+ repomonStatus.getDefPoint())*DefaultStatus.defValue) + DefaultStatus.defaultDef)
				.critical(((repomonStatus.getStartCritical() + repomonStatus.getCriticalPoint())*DefaultStatus.criticalValue) + DefaultStatus.defaultCritical)
				.hit(((repomonStatus.getStartHit() + repomonStatus.getHitPoint()) * DefaultStatus.hitValue) + DefaultStatus.defaultHit)
				.hp((int)(repomonStatus.getRepoExp() * DefaultStatus.hpValue)+DefaultStatus.defaultHp)
				.repomon(RepomonResponseDto.fromEntity(repomonStatus.getRepomon()))
				.build();
	}


	public static List<RepomonStatusResponseDto> fromEntityList(List<RepomonStatusEntity> repomonStatusList) {
		List<RepomonStatusResponseDto> result = new ArrayList<>();
		for (RepomonStatusEntity repomonStatus : repomonStatusList) {
			RepomonStatusResponseDto repomonStatusResponseDto = RepomonStatusResponseDto.fromEntity(repomonStatus);
			result.add(repomonStatusResponseDto);
		}
		return result;
	}
}
