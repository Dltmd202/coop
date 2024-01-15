package org.kt.parttime.parttime.dto;

import lombok.Data;
import org.kt.parttime.parttime.entity.PartTimeGroup;

@Data
public class PartTimeGroupBriefDto {
    private Long id;
    private String name;
    private Integer year;
    private Integer semester;

    public PartTimeGroupBriefDto(PartTimeGroup partTimeGroup){
        this.id = partTimeGroup.getId();
        this.year = partTimeGroup.getYear();
        this.semester = partTimeGroup.getSemester();
        this.name = partTimeGroup.getName();
    }
}
