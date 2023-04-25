package com.repomon.rocketdan.domain.repomon.repository;

import com.repomon.rocketdan.domain.repomon.entity.RepomonStatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RepomonStatusRepository extends JpaRepository<RepomonStatusEntity, Long> {

    @Query("select r from RepomonStatusEntity r order by abs(r.rating- :rating)")
    Page<RepomonStatusEntity> findByRatingOrderByAbsRating(@Param("rating") Integer rating, Pageable pageable);


    /**
     * TODO
     * List<RepomonStatusEntity> findAllByRatingBetween(Integer startRating, Integer endRating);
     */

}
