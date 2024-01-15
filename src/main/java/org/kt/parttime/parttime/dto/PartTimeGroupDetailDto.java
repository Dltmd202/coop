package org.kt.parttime.parttime.dto;

import lombok.Getter;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.dto.StudentWageDto;
import org.kt.parttime.utils.PriceUtils;

import java.util.List;
import java.util.Objects;

@Getter
public class PartTimeGroupDetailDto {
    private Long id;
    private String name;
    private Integer year;
    private Integer semester;
    private Integer holidayAllowance;
    private List<PartTimeDto> partTimes;
    private List<StudentWageDto> students;

    public PartTimeGroupDetailDto(
            PartTimeGroup partTimeGroup,
            List<StudentWageDto> students
    ){
        id = partTimeGroup.getId();
        name = partTimeGroup.getName();
        year = partTimeGroup.getYear();
        semester = partTimeGroup.getSemester();
        holidayAllowance = partTimeGroup.getHolidayAllowance();
        this.partTimes = partTimeGroup.getPartTimes().stream()
                .map(PartTimeDto::new).toList();
        this.students = students;
    }

    public String getFormattedHolidayAllowance(){
        return PriceUtils.format(holidayAllowance);
    }

    public boolean isSubPartTime(){
        if(Objects.isNull(partTimes)) return false;
        return !partTimes.isEmpty();
    }
}
