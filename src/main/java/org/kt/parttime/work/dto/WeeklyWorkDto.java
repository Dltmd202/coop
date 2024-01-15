package org.kt.parttime.work.dto;

import lombok.Getter;
import lombok.ToString;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.work.entity.Work;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class WeeklyWorkDto {
    private Integer week;
    private List<WorkDetailDto> works;
    private Integer wage;
    private Integer pureWage;
    private Integer weeklyHolidayAllowance;

    public WeeklyWorkDto(PartTimeGroup partTimeGroup, List<Work> works){
        this.week = works.stream()
                .map(Work::getWeek)
                .distinct()
                .findFirst()
                .orElseGet(() -> 0);
        this.works = works.stream()
                .map(WorkDetailDto::new)
                .collect(Collectors.toList());

        this.pureWage = this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .map(WorkDetailDto::getTotalWage)
                .mapToInt(Integer::valueOf)
                .sum();

        this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .reduce(WorkDetailDto.dummy, WorkDetailDto::accumulate);

        this.weeklyHolidayAllowance = partTimeGroup.calculateHolidayAllowance(works);
        this.wage = this.pureWage + this.weeklyHolidayAllowance;
    }


    public String getFormattedWage(){
        return PriceUtils.format(this.wage);
    }

    public String getFormattedPureWage(){
        return PriceUtils.format(this.pureWage);
    }

    public String getFormattedWeeklyHolidayAllowance(){
        return PriceUtils.format(this.weeklyHolidayAllowance);
    }
}
