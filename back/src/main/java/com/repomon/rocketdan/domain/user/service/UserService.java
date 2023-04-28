package com.repomon.rocketdan.domain.user.service;


import com.repomon.rocketdan.common.GHUtils;
import com.repomon.rocketdan.domain.repo.app.RepoDetail;
import com.repomon.rocketdan.domain.repo.app.RepoListItem;
import com.repomon.rocketdan.domain.repo.entity.ActiveRepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.repository.ActiveRepoRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import com.repomon.rocketdan.domain.user.dto.RepresentRepomonRequestDto;
import com.repomon.rocketdan.domain.user.dto.UserResponseDto;
import com.repomon.rocketdan.domain.user.entity.UserCardEntity;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserCardRepository;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import com.repomon.rocketdan.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.GHRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

import static com.repomon.rocketdan.exception.ErrorCode.*;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final ActiveRepoRepository activeRepoRepository;
	private final RepoRepository repoRepository;
	private final UserRepository userRepository;
	private final UserCardRepository userCardRepository;
	private final GHUtils ghUtils;


	public void modifyRepresentRepo(Long userId, RepresentRepomonRequestDto requestDto) {

		UserEntity user = userRepository.findById(userId).orElseThrow(
			() -> new CustomException(NOT_FOUND_ENTITY)
		);
		RepoEntity repo = repoRepository.findById(requestDto.getRepoId()).orElseThrow(
			() -> new CustomException(NOT_FOUND_REPOSITORY)
		);
		ActiveRepoEntity activeRepo = activeRepoRepository.findByRepoAndUser(repo, user)
			.orElseThrow(
				() -> new CustomException(NOT_FOUND_ENTITY)
			);

		user.updateRepresentRepo(activeRepo);
		userRepository.save(user);

	}


	public UserResponseDto getUserInfo(Long userId) {
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_FOUND_USER));
		Map<String, String> userInfo = ghUtils.getUser(user.getUserName());

		UserResponseDto userResponseDto = UserResponseDto.fromEntity(user, userInfo);

		// 총 경험치 조회
		UserCardEntity userCard = userCardRepository.findById(userId).orElseThrow(() -> new CustomException(NOT_FOUND_ENTITY));
		userResponseDto.setTotalExp(userCard.getTotalExp());

		// 깃허브에 레포 정보 조회
		Map<String, GHRepository> repositories = ghUtils.getRepositoriesWithName(user.getUserName());
		user.getRepresentRepo().ifPresent(activeRepoEntity -> {
			GHRepository ghRepository = repositories.get(activeRepoEntity.getRepo().getRepoKey());
			RepoDetail repoDetail = ActiveRepoEntity.convertToRepo(activeRepoEntity, ghRepository);
			RepoListItem repoListItem = RepoListItem.convertFromDetail(repoDetail);
			userResponseDto.setRepresentRepo(repoListItem);
		});
		return userResponseDto;
	}

}
