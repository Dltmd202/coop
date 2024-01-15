package org.kt.parttime.work.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.work.entity.Work;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static lombok.AccessLevel.*;


@Getter
@ToString
@NoArgsConstructor(access = PRIVATE)
public class WorkDetailDto {
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    static WorkDetailDto dummy = new WorkDetailDummyDto();

    private static class WorkDetailDummyDto extends WorkDetailDto{
        WorkDetailDummyDto(){
            super();
            super.workTime = 0.F;
            super.accumulatedWorkTime = 0.F;
            super.accumulatedWeekdayWorkTime = 0.F;
            super.overWorkTime = 0.F;
            super.nightWorkTime = 0.F;
            super.pureWage = 0;
            super.nightWage = 0;
            super.overTimeWage = 0;
            super.totalWage = 0;
            super.accumulatedTotalWage = 0;
        }
        @Override
        public boolean isWeekend() {
            return true;
        }
    }

    private Long id;
    private String partTimeName;
    private Integer partTimeHourPrice;
    private Integer nightHourPrice;
    private Integer overHourPrice;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Float workTime;
    private Float accumulatedWorkTime = 0.F;
    private Float accumulatedWeekdayWorkTime = 0.F;
    private Float overWorkTime;
    private Float nightWorkTime;
    private Integer week;
    private Integer pureWage;
    private Integer nightWage;
    private Integer overTimeWage;
    private Integer totalWage;
    private Integer accumulatedTotalWage = 0;
    private String status;

    public WorkDetailDto(Work work){
        this.id = work.getId();
        this.partTimeHourPrice = work.getPartTime().getHourPrice();
        this.nightHourPrice = work.getPartTime().getNightAllowance();
        this.overHourPrice = work.getPartTime().getOvertimeAllowance();
        this.partTimeName = work.getPartTime().getName();
        this.startTime = work.getStartTime();
        this.endTime = work.getEndTime();
        this.workTime = work.getWorkTime();
        this.overWorkTime = work.getOverWorkTime();
        this.nightWorkTime = work.getNightWorkTime();
        this.week = work.getWeek();
        this.nightWage = work.calculateDailyNightAllowance();
        this.overTimeWage = work.calculateDailyOvertimeAllowance();
        this.pureWage = work.calculateDailyPureWage();
        this.totalWage = this.nightWage + this.overTimeWage + this.pureWage;
        this.status = work.getStatus().name();
    }

    public WorkDetailDto accumulate(WorkDetailDto target){
        if(!target.nonRejected()) return target;
        target.accumulatedWorkTime = this.accumulatedWorkTime + target.workTime;
        target.accumulatedTotalWage = this.accumulatedTotalWage + target.totalWage;
        if(!target.isWeekend()) target.accumulatedWeekdayWorkTime = this.accumulatedWeekdayWorkTime + target.workTime;
        return target;
    }

    public String getFormattedTotalWage(){
        return PriceUtils.format(this.totalWage);
    }

    public String getFormattedAccumulatedTotalAage(){
        if(!nonRejected()) return "-";
        return PriceUtils.format(this.accumulatedTotalWage);
    }

    public Integer getDay(){
        return this.startTime.getDayOfMonth();
    }

    public String getFormattedStartTime(){
        return timeFormatter.format(startTime);
    }

    public String getFormattedEndTime(){
        return timeFormatter.format(endTime);
    }

    public boolean nonRejected(){
        return !this.status.equals("REJECTED");
    }

    public String getFormattedDayOfWeek(){
        DayOfWeek dayOfWeek = startTime.getDayOfWeek();
        if(dayOfWeek == DayOfWeek.MONDAY) return "월";
        else if(dayOfWeek == DayOfWeek.TUESDAY) return "화";
        else if(dayOfWeek == DayOfWeek.WEDNESDAY) return "수";
        else if(dayOfWeek == DayOfWeek.THURSDAY) return "목";
        else if(dayOfWeek == DayOfWeek.FRIDAY) return "금";
        else if(dayOfWeek == DayOfWeek.SATURDAY) return "토";
        else if(dayOfWeek == DayOfWeek.SUNDAY) return "일";
        return "";
    }

    public boolean isWeekend(){
        DayOfWeek dayOfWeek = startTime.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    public String getFormattedNightWorkTime(){
        if (nightWorkTime == 0.F) return "-";
        return String.format("%.1fH", nightWorkTime);
    }

    public String getFormattedWorkTime(){
        if (workTime == 0.F) return "-";
        return String.format("%.1fH", workTime);
    }

    public String getFormattedOverWorkTime(){
        if (overWorkTime == 0.F) return "-";
        return String.format("%.1fH", overWorkTime);
    }

    public String getAccumulatedWeekdayWorkTime(){
        if(accumulatedWeekdayWorkTime <= 0.F) return "-";
        return String.format("%.1fH", accumulatedWeekdayWorkTime);
    }

    public String statusStyle(){
        if(Objects.equals(status, "PENDING")) return "";
        else if(Objects.equals(status, "REJECTED")) return "table-danger";
        else return "table-success";
    }

    public String getFormattedPartTimeHourPrice(){
        return PriceUtils.format(this.partTimeHourPrice);
    }

}
