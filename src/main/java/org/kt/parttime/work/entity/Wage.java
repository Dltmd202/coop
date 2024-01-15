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
    private PartTimeGroup pratTimeGroup;

    @Column
    private Integer year;

    @Column
    private Integer month;

    @Column(nullable = false)
    private Integer firstWeekWage = 0;

    @Column(nullable = false)
    private Integer secondWeekWage = 0;

    @Column(nullable = false)
    private Integer thirdWeekWage = 0;

    @Column(nullable = false)
    private Integer fourthWeekWage = 0;

    @Column(nullable = false)
    private Integer fifthWeekWage = 0;

    @Column(nullable = false)
    private Integer sixthWeekWage = 0;

    public Wage(Student student, PartTimeGroup pratTimeGroup, Integer year, Integer month) {
        this.student = student;
        this.pratTimeGroup = pratTimeGroup;
        this.year = year;
        this.month = month;
    }

    public void updateWage(int month, int wage){
        if(month == 1) this.firstWeekWage = wage;
        else if(month == 2) this.secondWeekWage = wage;
        else if(month == 3) this.thirdWeekWage = wage;
        else if(month == 4) this.fourthWeekWage = wage;
        else if(month == 5) this.fifthWeekWage = wage;
        else if(month == 6) this.sixthWeekWage = wage;
    }

    public int getMonthlyWage(){
        int wage = firstWeekWage;
        wage += secondWeekWage;
        wage += thirdWeekWage;
        wage += fourthWeekWage;
        wage += fifthWeekWage;
        wage += sixthWeekWage;
        return wage;
    }
}
