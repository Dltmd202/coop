package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.user.entity.Student;

@Getter
public class StudentWageDto extends StudentDto{
    private Integer wage;

    public StudentWageDto(Student user, Integer wage) {
        super(user);
        this.wage = wage;
    }

    public String getFormattedWage(){
        return PriceUtils.format(this.wage);
    }
}
