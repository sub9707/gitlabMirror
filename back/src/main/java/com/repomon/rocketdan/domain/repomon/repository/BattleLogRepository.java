package com.repomon.rocketdan.domain.repomon.repository;

import com.repomon.rocketdan.domain.repomon.entity.BattleLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleLogRepository extends JpaRepository<BattleLogEntity, Long> {
}
