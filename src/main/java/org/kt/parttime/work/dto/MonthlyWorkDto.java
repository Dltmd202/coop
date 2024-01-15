package org.kt.parttime.work.dto;

import lombok.Getter;
import lombok.ToString;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.dto.StudentDto;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.work.entity.Work;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
public class MonthlyWorkDto {
    private Map<Integer, WeeklyWorkDto> weeklyWorks;
    private Integer monthlyWage;

    public MonthlyWorkDto(List<Work> works, PartTimeGroup partTimeGroup){
        weeklyWorks = works.stream()
                .collect(Collectors.groupingBy(Work::getWeek,
                        Collectors.collectingAndThen(Collectors.toList(),
                                (List<Work> w) ->
                                        new WeeklyWorkDto(partTimeGroup, w))));

        this.monthlyWage = this.weeklyWorks.values().stream()
                .map(WeeklyWorkDto::getWage)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public String getFormattedMonthlyWage(){
        return PriceUtils.format(this.monthlyWage);
    }
}
