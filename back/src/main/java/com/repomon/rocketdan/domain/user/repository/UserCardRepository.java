package com.repomon.rocketdan.domain.user.repository;


import com.repomon.rocketdan.domain.user.entity.UserCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCardRepository extends JpaRepository<UserCardEntity, Long> {

	//	Page<UserCardEntity> findByUserNameContaining(String keyword, Pageable pageable);

}

