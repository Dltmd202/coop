package org.kt.parttime.user.dto;

import lombok.Data;
import org.kt.parttime.user.entity.Admin;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class AdminEditForm {
    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    private String repeatedPassword;

    @NotEmpty(message = "직함을 입력해주세요")
    private String position;

    public void isValidPassword(BindingResult errors) {
        if (!password.equals(repeatedPassword)) {
            errors.rejectValue("repeatedPassword", "PasswordMismatch", "비밀번호가 일치하지 않습니다");
        }
    }

    public AdminEditForm(String name, String email, String position) {
        this.name = name;
        this.email = email;
        this.position = position;
    }

    public static AdminEditForm from(Admin admin){
        return new AdminEditForm(admin.getName(), admin.getEmail(), admin.getPosition());
    }
}
