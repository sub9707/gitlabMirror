package com.repomon.rocketdan.domain.repo.repository;

import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRepository extends JpaRepository<RepoEntity, Long> {

    Optional<RepoEntity> findByRepoKey(String repoKey);
}
