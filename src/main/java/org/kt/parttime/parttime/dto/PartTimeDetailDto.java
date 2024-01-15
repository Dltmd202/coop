package org.kt.parttime.parttime.dto;

import lombok.Getter;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.user.dto.StudentWageDto;

import java.util.List;

@Getter
public class PartTimeDetailDto {
    private Long id;
    private String name;
    private Integer year;
    private Integer semester;
    private Integer hourPrice;
    private Integer overtimeAllowance;
    private Integer nightAllowance;
    private List<StudentWageDto> students;

    public PartTimeDetailDto(PartTime partTime, List<StudentWageDto> students){
        this.id = partTime.getId();
        this.name = partTime.getName();
        this.year = partTime.getYear();
        this.semester = partTime.getSemester();
        this.hourPrice = partTime.getHourPrice();
        this.overtimeAllowance = partTime.getOvertimeAllowance();
        this.nightAllowance = partTime.getNightAllowance();
        this.students = students;
    }

    public String getFormattedHourPrice(){
        return PriceUtils.format(this.hourPrice);
    }

    public String getFormattedOverTimeAllowance(){
        return PriceUtils.format(overtimeAllowance);
    }

    public String getFormattedNightAllowance(){
        return PriceUtils.format(nightAllowance);
    }
}
