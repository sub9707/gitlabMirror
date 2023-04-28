package com.repomon.rocketdan.domain.repo.repository;


import com.repomon.rocketdan.domain.repo.entity.RepomonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RepomonRepository extends JpaRepository<RepomonEntity, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM repomon WHERE repomon_ ORDER BY RAND() limit 3")
	List<RepomonEntity> findTop3ByRandom();

}
