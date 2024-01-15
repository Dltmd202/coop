package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.user.entity.Student;

@Getter
public class StudentDto extends UserDto{
    private String studentId;
    private String phoneNumber;
    private String account;
    private String status;
    private String department;
    private Integer grade;
    private Integer semester;
    private String bank;

    public StudentDto(Student user) {
        super(user);
        this.studentId = user.getStudentId();
        this.phoneNumber = user.getPhoneNumber();
        this.bank = user.getBank();
        this.account = user.getAccount();
        this.status = user.getStatus().getDesc();
        this.department = user.getDepartment();
        this.grade = user.getGrade();
        this.semester = user.getSemester();
    }
}
