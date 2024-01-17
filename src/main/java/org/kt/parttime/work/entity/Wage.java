package org.kt.parttime.work.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kt.parttime.common.entity.BaseTimeEntity;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.entity.Student;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Wage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "part_time_group_id")
    private PartTimeGroup partTimeGroup;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "part_time_id")
    private PartTime partTime;

    @Column
    private Integer year;

    @Column
    private Integer month;

    @Column(nullable = false)
    private double workTime;

    @Column(nullable = false)
    private Integer hourPrice = 0;

    public Wage(Student student, PartTimeGroup partTimeGroup, Integer year, Integer month) {
        this.student = student;
        this.partTimeGroup = partTimeGroup;
        this.year = year;
        this.month = month;
    }

    public Wage(Student student, PartTimeGroup partTimeGroup, PartTime partTime, Integer year, Integer month) {
        this.student = student;
        this.partTimeGroup = partTimeGroup;
        this.partTime = partTime;
        this.year = year;
        this.month = month;
    }

    public void updateWorkTime(double workTime, int hourPrice){
        this.workTime = workTime;
        this.hourPrice = hourPrice;
    }

    public int getMonthlyWage(){
        return (int) Math.round(workTime * hourPrice);
    }
}
