package org.kt.parttime.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.kt.parttime.user.entity.AcademicStatus;
import org.kt.parttime.user.entity.Student;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class StudentForm {
    @NotEmpty(message = "이름을 입력해주세요")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "학번을 입력해주세요.")
    private String studentId;

    private String repeatedPassword;

    @NotEmpty(message = "전화번호를 입력해주세요")
    private String phoneNumber;

    @NotEmpty(message = "은행을 입력해주세요")
    private String bank;

    @NotEmpty(message = "계좌번호를 입력해주세요")
    private String account;

    @NotNull(message = "학적상태를 선택해주세요")
    private AcademicStatus status;

    @NotEmpty(message = "학부를 입력해주세요")
    private String department;

    @NotNull(message = "학년을 입력해주세요")
    private Integer grade;

    @NotNull(message = "학기를 입력해주세요")
    private Integer semester;

    public void validatePassword(BindingResult errors) {
        if (!password.equals(repeatedPassword)) {
            errors.rejectValue("repeatedPassword", "PasswordMismatch", "비밀번호가 일치하지 않습니다");
        }
    }

    public StudentForm(StudentDto student){
        this.name = student.getName();
        this.email = student.getEmail();
        this.studentId = student.getStudentId();
        this.phoneNumber = student.getPhoneNumber();
        this.account = student.getAccount();
        this.status = AcademicStatus.detailValueOf(student.getStatus());
        this.department = student.getDepartment();
        this.grade = student.getGrade();
        this.semester = student.getSemester();
    }

    public Student toModel(){
        return new Student(name, email, password, studentId, phoneNumber, account, status, department, grade, semester);
    }
}
