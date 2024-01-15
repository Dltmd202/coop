package org.kt.parttime.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class StudentLoginForm {
    @NotEmpty(message = "이메일을 입력해주세요")
    String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    String password;
}
