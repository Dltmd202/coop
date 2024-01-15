package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.utils.PriceUtils;

@Getter
public class StudentWageDetailDto extends StudentDto{
    private Integer wage;
    private String partTimeGroupName;

    public StudentWageDetailDto(PartTimeGroup partTimeGroup, Student user, Integer wage) {
        super(user);
        this.partTimeGroupName = partTimeGroup.getName();
        this.wage = wage;
    }

    public String getFormattedWage(){
        return PriceUtils.format(this.wage);
    }
}
