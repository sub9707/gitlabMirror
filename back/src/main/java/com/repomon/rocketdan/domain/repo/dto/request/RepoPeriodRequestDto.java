package com.repomon.rocketdan.domain.repo.dto.request;


import java.time.LocalDateTime;
import lombok.Data;


@Data
public class RepoPeriodRequestDto {

    private LocalDateTime startedAt;
    private LocalDateTime endAt;
}
