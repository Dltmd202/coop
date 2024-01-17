package org.kt.parttime.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kt.parttime.user.dto.StudentForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Student extends User{

    /**
     * 학번
     */
    @Column(length = 20, unique = true)
    private String studentId;

    /**
     * 전화번호
     */
    @Column(length = 30)
    private String phoneNumber;

    /**
     * TODO 관련 작업
     * 은행
     */
    @Column(length = 30)
    private String bank;

    /**
     * 계좌번호
     */
    @Column(length = 100)
    private String account;

    /**
     * 재적상태
     */
    @Column
    @Enumerated(EnumType.STRING)
    private AcademicStatus status;

    /**
     * 학부
     */
    @Column(length = 30)
    private String department;

    /**
     * 학년
     */
    @Column
    private Integer grade;

    /**
     * 학기
     */
    @Column
    private Integer semester;

    public Student(String name, String email, String password, String studentId, String phoneNumber, String account, AcademicStatus status, String department, Integer grade, Integer semester, String bank){
        super(name, email, password, UserRole.STUDENT);
        this.studentId = studentId;
        this.phoneNumber = phoneNumber;
        this.account = account;
        this.status = status;
        this.department = department;
        this.grade = grade;
        this.semester = semester;
        this.bank = bank;
    }

    @Override
    public String getDetailPage() {
        return "student/studentDetail";
    }

    public void update(StudentForm form){
        super.name = form.getName();
        this.email = form.getEmail();
        this.studentId = form.getStudentId();
        this.phoneNumber = form.getPhoneNumber();
        this.account = form.getAccount();
        this.status = form.getStatus();
        this.department = form.getDepartment();
        this.grade = form.getGrade();
        this.semester = form.getSemester();
    }
}
