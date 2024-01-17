package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.utils.PriceUtils;
import org.kt.parttime.work.entity.Wage;

@Getter
public class StudentWageDetailDto extends StudentDto{
    private Integer wage;
    private String partTimeGroupName;
    private Integer hourPrice;
    private Double workTime;

    public StudentWageDetailDto(PartTimeGroup partTimeGroup, Student user, Wage wage) {
        super(user);
        this.partTimeGroupName = partTimeGroup.getName();
        this.hourPrice = wage.getHourPrice();
        this.wage = wage.getMonthlyWage();
        this.workTime = wage.getWorkTime();
    }

    public String getFormattedWage(){
        return PriceUtils.format(this.wage);
    }
}
