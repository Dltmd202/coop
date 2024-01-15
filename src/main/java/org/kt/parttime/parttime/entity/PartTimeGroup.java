package org.kt.parttime.parttime.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kt.parttime.common.entity.BaseTimeEntity;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.work.entity.Work;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartTimeGroup extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column
    private Integer year;

    @Column
    private Integer semester;

    @OneToMany(mappedBy = "partTimeGroup", cascade = CascadeType.ALL)
    private List<PartTime> partTimes = new ArrayList<>();

    @OneToMany(mappedBy = "partTimeGroup", cascade = CascadeType.ALL)
    private List<StudentPartTimeGroup> students;

    public PartTimeGroup(String name, Integer year, Integer semester){
        this.name = name;
        this.year = year;
        this.semester = semester;
    }

    public void joinPartTime(PartTime partTime){
        partTimes.add(partTime);
        partTime.setPartTimeGroup(this);
    }

    public StudentPartTimeGroup join(Student student){
        StudentPartTimeGroup studentPartTime = new StudentPartTimeGroup(student, this);
        this.students.add(studentPartTime);
        return studentPartTime;
    }

    /**
     * TODO 15시간 이상
     * @param works
     * @return
     */
    public int calculateHolidayAllowance(List<Work> works){
        double weekDayTotalWorkTime = works.stream()
                .filter(Work::isWeekDay)
                .filter(Work::nonRejected)
                .map(Work::getWorkTime)
                .mapToDouble(Double::valueOf)
                .sum();
        return weekDayTotalWorkTime >= 15 ? (int) ((weekDayTotalWorkTime / 5) * getHolidayAllowance()) : 0;
    }

    public int calculateWeeklyWage(List<Work> works){
        int weeklyWage = works.stream()
                .map(Work::calculateDailyWage)
                .mapToInt(Integer::intValue)
                .sum();
        weeklyWage += this.calculateHolidayAllowance(works);
        return weeklyWage;
    }

    public Integer getHolidayAllowance(){
        return this.partTimes.stream()
                .map(PartTime::getHourPrice)
                .max(Comparator.comparingInt(Integer::valueOf))
                .orElse(0);
    }
}
