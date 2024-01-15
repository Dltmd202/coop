package org.kt.parttime.common.dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

@Data
public class TimeQuery {
    private Integer year;
    private Integer month;

    protected TimeQuery(Integer year, Integer month){
        this.year = year;
        this.month = month;
    }

    public static TimeQuery of(Integer year, Integer month){
        if(Objects.isNull(year) || Objects.isNull(month)){
            return new TimeQuery(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth().getValue());
        }
        return new TimeQuery(year, month);
    }

    public TimeQuery next(){
        YearMonth currentYearMonth = YearMonth.of(year, month);
        YearMonth nextYearMonth = currentYearMonth.plusMonths(1);

        return new TimeQuery(nextYearMonth.getYear(), nextYearMonth.getMonthValue());
    }

    public TimeQuery prev(){
        YearMonth currentYearMonth = YearMonth.of(year, month);
        YearMonth previousYearMonth = currentYearMonth.minusMonths(1);

        return new TimeQuery(previousYearMonth.getYear(), previousYearMonth.getMonthValue());

    }

    public LocalDateTime thisLocalDateTime(){
        return LocalDateTime.of(year, month, 1, 0, 0, 0);
    }

    public LocalDateTime thisLocalDateTimeFirstDayOfWeek(int week){
        LocalDateTime time = thisLocalDateTime().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        if(thisLocalDateTime().isAfter(time.plusWeeks(week - 1))) return thisLocalDateTime();
        return time.plusWeeks(week - 1);
    }

    public LocalDateTime thisLocalDateTimeLastDayOfWeek(int week){
        LocalDateTime time = thisLocalDateTimeFirstDayOfWeek(week).with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        if(nextLocalDateTime().isBefore(time) || nextLocalDateTime().isEqual(time))
            return nextLocalDateTime().minusDays(1).withHour(23).withMinute(59);
        return time.withHour(23).withMinute(59);
    }

    public LocalDateTime nextLocalDateTime(){
        return this.thisLocalDateTime().plusMonths(1);
    }
}
