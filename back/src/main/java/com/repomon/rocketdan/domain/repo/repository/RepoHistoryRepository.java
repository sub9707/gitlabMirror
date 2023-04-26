package com.repomon.rocketdan.domain.repo.repository;

import com.repomon.rocketdan.domain.repo.entity.RepoHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoHistoryRepository extends JpaRepository<RepoHistoryEntity, Long> {

}
