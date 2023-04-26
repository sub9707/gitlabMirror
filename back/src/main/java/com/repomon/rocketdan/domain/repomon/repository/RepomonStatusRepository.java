package com.repomon.rocketdan.domain.repomon.repository;

import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface RepomonStatusRepository extends JpaRepository<RepomonStatusEntity, Long> {

//    // Rating이 가장 가까운 값 추출하기
//    @Query("select r from RepomonStatusEntity r order by abs(r.rating- :rating)")
//    Page<RepomonStatusEntity> findByRatingOrderByAbsRating(@Param("rating") Integer rating, Pageable pageable);

    // 범위 안에서 랜덤으로 한 가지 추출하기
    @Query(nativeQuery = true, value = "SELECT * FROM rocketdan.repomon_status_entity INNER JOIN rocketdan.repo_entity " +
            "ON rocketdan.repomon_status_entity.repo_id = rocketdan.repo_entity.repo_id " +
            "WHERE rocketdan.repomon_status_entity.rating >= :startRating and rocketdan.repomon_status_entity.rating < :endRating and rocketdan.repomon_status_entity.repo_id != :repoId Order By RAND() limit 1 ")
    Optional<RepomonStatusEntity> findByRatingBetweenRandom(@Param("startRating") Integer startRating, @Param("endRating") Integer endRating, @Param("repoId") Long repoId);


}
