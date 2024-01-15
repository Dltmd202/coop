package org.kt.parttime.parttime.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PartTimeGroupForm {
    @NotEmpty(message = "근로장학 그룹의 이름을 입력해주세요")
    private String name;
}
