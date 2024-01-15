package org.kt.parttime.work.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class WorkForm {
    @NotNull(message = "시작시간을 입력해주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @NotNull(message = "종료시간을 입력해주세요")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    @NotNull(message = "근로장학 세부분류를 선택해주세요")
    private Long partTimeId;

    public WorkForm(Integer year, Integer month){
        if(Objects.isNull(year)) year = LocalDateTime.now().getYear();
        if(Objects.isNull(month)) month = LocalDateTime.now().getMonth().getValue();

        int day = 1;
        if(LocalDateTime.now().getYear() == year && LocalDateTime.now().getMonth().getValue() == month)
            day = LocalDateTime.now().getDayOfMonth();

        this.startTime = LocalDateTime.of(year, month, day, 0, 0);
        this.endTime = LocalDateTime.of(year, month, day, 0, 0);
    }

    public Integer getYear(){
        return startTime.getYear();
    }

    public Integer getMonth(){
        return startTime.getMonth().getValue();
    }

    public void validateTime(BindingResult errors){
        if(Objects.isNull(startTime) || Objects.isNull(endTime)) return;
        Duration duration = Duration.between(startTime, endTime);
        if(duration.toHours() >= 24)
            errors.rejectValue("endTime", "endTime", "근로시간은 24시간이상 입력이 불가능합니다.");

    }
}
