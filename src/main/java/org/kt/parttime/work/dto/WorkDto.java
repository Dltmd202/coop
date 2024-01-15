package org.kt.parttime.work.dto;

import lombok.Getter;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.work.entity.Work;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class WorkDto {
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private Long id;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private Float workTime;
    private Float nightWorkTime;
    private Integer month;

    public WorkDto(Work work){
        this.id = work.getId();
        this.startTime = work.getStartTime();
        this.endTime = work.getEndTime();
        this.workTime = work.getWorkTime();
        this.nightWorkTime = work.getNightWorkTime();
    }

    public Integer getDay(){
        return this.startTime.getDayOfMonth();
    }

    public int getYear(){
        return startTime.getYear();
    }

    public int getMonth(){
        return startTime.getMonth().getValue();
    }

    public String getFormattedStartTime(){
        return timeFormatter.format(startTime);
    }

    public String getFormattedEndTime(){
        return timeFormatter.format(endTime);
    }

    public static Integer extractStartYear(List<WorkDto> works){
        return works.stream()
                .map(WorkDto::getYear)
                .distinct()
                .findFirst()
                .orElseGet(() -> LocalDateTime.now().getYear());
    }

    public static Integer extractStartMonth(List<WorkDto> works){
        return works.stream()
                .map(WorkDto::getMonth)
                .distinct()
                .findFirst()
                .orElseGet(() -> LocalDateTime.now().getYear());
    }
}
