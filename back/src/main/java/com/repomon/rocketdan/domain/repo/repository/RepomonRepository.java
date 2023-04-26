package com.repomon.rocketdan.domain.repo.repository;


import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RepomonRepository extends JpaRepository<RepomonEntity, Long> {
}
