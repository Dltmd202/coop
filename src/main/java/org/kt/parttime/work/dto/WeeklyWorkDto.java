package org.kt.parttime.work.dto;

import lombok.Getter;
import lombok.ToString;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.work.entity.Work;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class WeeklyWorkDto {
    private Integer year;
    private Integer month;
    private Integer week;
    private List<WorkDetailDto> works;

    // 주휴수당이 포함된 주급
    private Integer wage;

    // 주휴수당이 포함되지 않은 주급
    private Integer pureWeeklyWage;


    private Long holidayAllowanceTargetPartTimeId;

    // 적용할 주휴수당
    private Integer holidayAllowance;

    // 해당 주의 주휴수당
    private Integer weeklyHolidayAllowance;

    public WeeklyWorkDto(List<Work> works){
        this.week = extractWeek(works);

        this.year = works.stream()
                .map(Work::getYear)
                .findFirst()
                .orElseGet(() -> 1);

        this.month = works.stream()
                .map(Work::getMonth)
                .findFirst()
                .orElseGet(() -> 1);


        this.works = works.stream()
                .map(WorkDetailDto::new)
                .collect(Collectors.toList());

        this.pureWeeklyWage = this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .map(WorkDetailDto::getWageIncludeNightAllowance)
                .mapToInt(Integer::valueOf)
                .sum();

        this.holidayAllowanceTargetPartTimeId = this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .filter(WorkDetailDto::isWeekDay)
                .max((w1, w2) -> w1.getPartTimeHourPrice() - w2.getPartTimeHourPrice())
                .map(WorkDetailDto::getPartTimeId)
                .orElseGet(() -> 0L);

        this.holidayAllowance = this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .filter(WorkDetailDto::isWeekDay)
                .max((w1, w2) -> w1.getPartTimeHourPrice() - w2.getPartTimeHourPrice())
                .map(WorkDetailDto::getPartTimeHourPrice)
                .orElseGet(() -> 0);

        this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .reduce(WorkDetailDto.dummy, WorkDetailDto::accumulate);

        this.weeklyHolidayAllowance = calculateHolidayAllowance();
        this.wage = this.pureWeeklyWage + this.weeklyHolidayAllowance;
    }

    public void updateOverTimeWage(){
        this.pureWeeklyWage += this.works.stream()
                .map(WorkDetailDto::getOverTimeWage)
                .mapToInt(Integer::intValue)
                .sum();
        this.wage = this.pureWeeklyWage + this.weeklyHolidayAllowance;
    }

    public void noOverTimeWage(){
        this.works.forEach(WorkDetailDto::noOverTimeWork);
        this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .reduce(WorkDetailDto.dummy, WorkDetailDto::accumulate);
    }

    private static Integer extractWeek(List<Work> works) {
        return works.stream()
                .map(Work::getWeek)
                .distinct()
                .findFirst()
                .orElseGet(() -> 0);
    }

    public int calculateHolidayAllowance(){
        double weekdayWorkTime = getHolidayAllowanceBasedWeekdayWorkTime();
        return (int) Math.round((weekdayWorkTime / 5) * (double) holidayAllowance);
    }

    public double getWeekdayWorkTime(){
        return works.stream()
                .filter(WorkDetailDto::isWeekDay)
                .filter(WorkDetailDto::nonRejected)
                .map(WorkDetailDto::getWorkTime)
                .mapToDouble(Double::valueOf)
                .sum();
    }

    public double getHolidayAllowanceBasedWeekdayWorkTime(){
        double weekdayWorkTime = getWeekdayWorkTime();
        return weekdayWorkTime >= 15 ? weekdayWorkTime : 0.;
    }

    public boolean overtimeSuitabilityEvaluation(){
        long count = this.works.stream()
                .filter(WorkDetailDto::nonRejected)
                .filter(w -> w.getWorkTime() >= 8)
                .count();
        return count >= (int) Math.max(Math.floor((double) getDayOfDate(year, month, week) * 0.75), 1);
    }


    public String getFormattedWage(){
        return PriceUtils.format(this.wage);
    }

    public String getFormattedPureWage(){
        return PriceUtils.format(this.pureWeeklyWage);
    }

    public String getFormattedWeeklyHolidayAllowance(){
        return PriceUtils.format(this.weeklyHolidayAllowance);
    }

    public String getFormattedHolidayAllowance(){
        return PriceUtils.format(this.holidayAllowance);
    }

    public double getConfirmedConvertedWorkHour(long partTimeId){
        double weeklyConvertedWorkTime = this.works.stream()
                .filter(WorkDetailDto::isConfirmed)
                .filter(w -> w.getPartTimeId() == partTimeId)
                .map(WorkDetailDto::getConvertedWorkTime)
                .mapToDouble(Double::valueOf)
                .sum();
        return partTimeId == holidayAllowanceTargetPartTimeId ?
                weeklyConvertedWorkTime + getHolidayAllowanceBasedWeekdayWorkTime() / 5:
                weeklyConvertedWorkTime;
    }

    public static int getDayOfDate(int year, int month, int week){
        LocalDate firstDayOfWeek = LocalDate.of(year, month, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))
                .plusWeeks(week - 1);
        LocalDate lastDayOfWeek = firstDayOfWeek.plusDays(6);

        return  (int) firstDayOfWeek.until(lastDayOfWeek.plusDays(1)).getDays();
    }
}
