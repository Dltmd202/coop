package org.kt.parttime.parttime.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kt.parttime.parttime.entity.PartTime;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class PartTimeEditForm {
    @NotEmpty(message = "근로장학 이름을 입력해주세요")
    private String name;

    @Min(value = 2000, message = "년도를 확인해주세요")
    @NotNull(message = "년도를 입력해주세요.")
    private Integer year;

    @Max(value = 9, message = "학기를 확인해주세요")
    @NotNull(message = "학기를 입력해주세요.")
    private Integer semester;

    @NotNull(message = "시급을 입력해주세요")
    private Integer hourPrice;

    @NotNull(message = "초과근무수당을 입력해주세요")
    private Integer overtimeAllowance;

    @NotNull(message = "야간근무수당을 입력해주세요")
    private Integer nightAllowance;

    public PartTimeEditForm(PartTime partTime){
        this.name = partTime.getName();
        this.year = partTime.getYear();
        this.semester = partTime.getSemester();
        this.hourPrice = partTime.getHourPrice();
        this.overtimeAllowance = partTime.getOvertimeAllowance();
        this.nightAllowance = partTime.getNightAllowance();
    }
}
