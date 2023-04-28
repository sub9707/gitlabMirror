package com.repomon.rocketdan.domain.repo.repository;


import com.repomon.rocketdan.domain.repo.entity.ActiveRepoEntity;
import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import com.repomon.rocketdan.domain.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ActiveRepoRepository extends JpaRepository<ActiveRepoEntity, Long> {

	Page<ActiveRepoEntity> findByUser(UserEntity userEntity, Pageable pageable);
	Optional<ActiveRepoEntity> findByRepoAndUser(RepoEntity repoEntity, UserEntity userEntity);
	Long countByUser_UserId(Long userId);

}
