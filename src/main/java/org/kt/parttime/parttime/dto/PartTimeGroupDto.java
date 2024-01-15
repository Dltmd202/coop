package org.kt.parttime.parttime.dto;

import lombok.Getter;
import org.kt.parttime.parttime.entity.PartTimeGroup;

@Getter
public class PartTimeGroupDto {
    private Long id;
    private String name;
    private Integer holidayAllowance;
    private Integer year;
    private Integer semester;

    public PartTimeGroupDto(PartTimeGroup partTimeGroup){
        this.id = partTimeGroup.getId();
        this.name = partTimeGroup.getName();
        this.holidayAllowance = partTimeGroup.getHolidayAllowance();
        this.year = partTimeGroup.getYear();
        this.semester = partTimeGroup.getSemester();
    }
}
