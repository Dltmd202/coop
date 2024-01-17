package org.kt.parttime.parttime.entity;

import lombok.*;
import org.kt.parttime.common.entity.BaseTimeEntity;
import org.kt.parttime.parttime.dto.PartTimeEditForm;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.work.entity.Work;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartTime extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String name;

    @Column
    private Integer year;

    @Column
    private Integer semester;

    @Column(nullable = false)
    private Integer hourPrice;

    @Column
    private Integer overtimeAllowance;

    @Column
    private Integer nightAllowance;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "part_time_group_id")
    private PartTimeGroup partTimeGroup;

    public PartTime(String name, Integer year, Integer semester, Integer hourPrice) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.hourPrice = hourPrice;
        this.overtimeAllowance = (int) ((double) hourPrice / 2);
        this.nightAllowance = (int) ((double) hourPrice / 2);
    }

    public PartTime(String name, Integer year, Integer semester, Integer hourPrice, PartTimeGroup partTimeGroup) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.hourPrice = hourPrice;
        this.overtimeAllowance = (int) ((double) hourPrice / 2);
        this.nightAllowance = (int) ((double) hourPrice / 2);
        this.partTimeGroup = partTimeGroup;
    }

    /**
     * 일급 계산
     * @param work
     * @return
     */
    public Integer calculateDailyWage(Work work){
        int wage = this.calculateDailyPureWage(work);
        wage += this.calculateDailyOvertimeAllowance(work);
        wage += this.calculateDailyNightAllowance(work);
        return wage;
    }

    public Integer calculateDailyPureWage(Work work){
        return (int) Math.round(work.getWorkTime() * hourPrice);
    }

    public Integer calculateDailyOvertimeAllowance(Work work){
        return (int) Math.round(work.getOverWorkTime() * overtimeAllowance);
    }

    public Integer calculateDailyNightAllowance(Work work){
        return (int) Math.round(work.getNightWorkTime() * nightAllowance);
    }

    /**
     * 주급 계산
     * @param works
     * @return
     */
    public Integer calculateWeeklyWage(List<Work> works){
        int wage = works.stream()
                .map(this::calculateDailyWage)
                .mapToInt(Integer::valueOf)
                .sum();
        wage += calculateHolidayAllowance(works);
        return wage;
    }

    public Integer calculateWeeklyPureWage(List<Work> works){
        return works.stream()
                .map(this::calculateDailyWage)
                .mapToInt(Integer::valueOf)
                .sum();
    }

    public Integer calculateMonthlyWage(List<Work> works){
        Map<Integer, List<Work>> collect = works.stream()
                .collect(Collectors.groupingBy(Work::getWeek));

        int wage = 0;
        for (List<Work> weeklyWork : collect.values()) {
            wage += calculateWeeklyWage(weeklyWork);
        }
        return wage;
    }


    /**
     * @param works
     * @return
     */
    public Integer calculateHolidayAllowance(List<Work> works){
        double weekDayTotalWorkTime = works.stream()
                .filter(Work::isWeekDay)
                .map(Work::getWorkTime)
                .mapToDouble(Double::valueOf)
                .sum();
        return weekDayTotalWorkTime > 15 ? (int) ((weekDayTotalWorkTime / 5) * hourPrice) : 0;
    }

    public void update(PartTimeEditForm partTimeForm){
        this.name = partTimeForm.getName();
        this.year = partTimeForm.getYear();
        this.semester = partTimeForm.getSemester();
        this.hourPrice = partTimeForm.getHourPrice();
        this.overtimeAllowance = partTimeForm.getOvertimeAllowance();
        this.nightAllowance = partTimeForm.getNightAllowance();
    }

}
