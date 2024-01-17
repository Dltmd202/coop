package org.kt.parttime.work.dto;

import lombok.Getter;
import lombok.ToString;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.work.entity.Work;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
public class MonthlyWorkDto {
    private Map<Integer, WeeklyWorkDto> weeklyWorks;
    private boolean isOverTime;
    private Integer monthlyWage;

    public MonthlyWorkDto(List<Work> works){
        weeklyWorks = works.stream()
                .collect(Collectors.groupingBy(Work::getWeek,
                        Collectors.collectingAndThen(Collectors.toList(), (List<Work> w) -> new WeeklyWorkDto(w))));

        isOverTime = isSuitableForOverTime();
        if(isOverTime) this.weeklyWorks.values().forEach(WeeklyWorkDto::updateOverTimeWage);
        else this.weeklyWorks.values().forEach(WeeklyWorkDto::noOverTimeWage);

        this.monthlyWage = this.weeklyWorks.values().stream()
                .map(WeeklyWorkDto::getWage)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public boolean isSuitableForOverTime(){
        return 4 <= this.weeklyWorks.values().stream()
                .filter(WeeklyWorkDto::overtimeSuitabilityEvaluation)
                .count();
    }

    public String getFormattedMonthlyWage(){
        return PriceUtils.format(this.monthlyWage);
    }

    public double getConvertedWorkHour(long partTimeId){
        double sum = weeklyWorks.values().stream()
                .map(w -> w.getConfirmedConvertedWorkHour(partTimeId))
                .mapToDouble(Double::valueOf)
                .sum();
        return sum;
    }
}
