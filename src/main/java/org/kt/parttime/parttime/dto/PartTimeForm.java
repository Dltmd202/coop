package org.kt.parttime.parttime.dto;

import lombok.Data;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public class PartTimeForm {

    @NotEmpty(message = "근로장학 이름을 입력해주세요")
    private String name;

    @Min(value = 2000, message = "년도를 확인해주세요")
    @NotNull(message = "년도를 입력해주세요.")
    private Integer year;

    @Max(value = 9, message = "학기를 확인해주세요")
    @NotNull(message = "학기를 입력해주세요.")
    private Integer semester;

    @NotNull(message = "시급을 입력해주세요.")
    private Integer hourPrice;

    private String groupType;

    private String groupCreate;

    private Long groupSelect;

    public boolean isTypeSelect(){
        return groupType.equalsIgnoreCase("select");
    }

    public PartTimeGroup makePartTimeGroup(){
        return new PartTimeGroup(groupCreate, year, semester);
    }

    public PartTime makePartTime(){
        return new PartTime(this.getName(), this.getYear(), this.getSemester(), this.getHourPrice());
    }

    public boolean isValidSelectGroup(){
        return !Objects.isNull(groupSelect);
    }

    public boolean isValidCreateGroup(){
        return StringUtils.hasText(groupCreate);
    }

    public boolean isTypeCreate(){
        return groupType.equalsIgnoreCase("create");
    }

    public void validatePartTimeGroup(BindingResult errors) {
        if(!StringUtils.hasText(groupType))
            errors.rejectValue("groupType", "groupType", "그룹 지정 방법을 선택하세요");
        else if(isTypeSelect() && !isValidSelectGroup())
            errors.rejectValue("groupSelect", "groupSelect", "그룹을 선택하세요");
        else if(isTypeCreate() && !isValidCreateGroup())
            errors.rejectValue("groupCreate", "groupCreate", "그룹을 이름을 입력하세요");
    }
}
