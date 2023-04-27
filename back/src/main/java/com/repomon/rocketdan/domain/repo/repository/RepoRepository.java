package com.repomon.rocketdan.domain.repo.repository;


import com.repomon.rocketdan.domain.repo.entity.RepoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RepoRepository extends JpaRepository<RepoEntity, Long> {

    Optional<RepoEntity> findByRepoKey(String repoKey);
    Page<RepoEntity> findByIsActiveTrue(Pageable pageable);
    Page<RepoEntity> findByRepoNameContainingAndIsActiveTrue(String search, Pageable pageable);

    Page<RepoEntity> findActiveRepositoriesOrderByRepomonRatingDesc(Pageable pageable);

}
