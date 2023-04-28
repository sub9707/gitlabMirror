package com.repomon.rocketdan.domain.user.service;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.repo.repository.ActiveRepoRepository;
import com.repomon.rocketdan.domain.repo.repository.RepoRepository;
import com.repomon.rocketdan.domain.user.dto.RepresentRepomonRequestDto;
import com.repomon.rocketdan.domain.user.entity.ActiveRepoEntity;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import com.repomon.rocketdan.domain.user.repository.UserRepository;
import com.repomon.rocketdan.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.repomon.rocketdan.exception.ErrorCode.NOT_FOUND_ENTITY;
import static com.repomon.rocketdan.exception.ErrorCode.NOT_FOUND_REPOSITORY;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

	private final ActiveRepoRepository activeRepoRepository;

	private final RepoRepository repoRepository;

	private final UserRepository userRepository;


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

}
