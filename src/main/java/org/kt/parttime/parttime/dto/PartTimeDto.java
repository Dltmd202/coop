package org.kt.parttime.parttime.dto;

import lombok.Getter;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.parttime.entity.PartTime;

@Getter
public class PartTimeDto {
    private Long id;
    private String name;
    private Integer year;
    private Integer semester;
    private Integer hourPrice;
    private Integer holidayAllowance;
    private Integer overtimeAllowance;
    private Integer nightAllowance;

    public PartTimeDto(PartTime partTime){
        this.id = partTime.getId();
        this.name = partTime.getName();
        this.year = partTime.getYear();
        this.semester = partTime.getSemester();
        this.hourPrice = partTime.getHourPrice();
        this.holidayAllowance = partTime.getPartTimeGroup().getHolidayAllowance();
        this.overtimeAllowance = partTime.getOvertimeAllowance();
        this.nightAllowance = partTime.getNightAllowance();
    }

    public String getFormattedHourPrice(){
        return PriceUtils.format(this.hourPrice);
    }

    public String getFormattedHolidayAllowance(){
        return PriceUtils.format(holidayAllowance);
    }

    public String getFormattedOverTimeAllowance(){
        return PriceUtils.format(overtimeAllowance);
    }

    public String getFormattedNightAllowance(){
        return PriceUtils.format(nightAllowance);
    }
}
