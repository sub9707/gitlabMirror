package com.repomon.rocketdan.domain.repo.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
public class RepoPeriodRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startedAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endAt;

    public LocalDateTime getStartedAt(){
        return startedAt.atStartOfDay();
    }

    public LocalDateTime getEndAt(){
        return startedAt.atStartOfDay();
    }
}
