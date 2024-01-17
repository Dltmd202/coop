package org.kt.parttime.work.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.kt.parttime.common.entity.BaseTimeEntity;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.parttime.entity.StudentPartTimeGroup;
import org.kt.parttime.user.entity.Admin;
import org.kt.parttime.user.entity.Student;

import javax.persistence.*;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.Locale;

import static javax.persistence.FetchType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@ToString(exclude = {"studentPartTimeGroup", "partTime", "student", "confirmer"})
@NoArgsConstructor(access = PROTECTED)
public class Work extends BaseTimeEntity {
    private static final float fullNightWork = 8F;
    private static final float fullDayWork = 16F;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_part_time_group_id")
    private StudentPartTimeGroup studentPartTimeGroup;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "part_time_group_id")
    private PartTimeGroup partTimeGroup;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "part_time_id")
    private PartTime partTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private Float workTime;

    @Column
    private Float nightWorkTime;

    @Column
    private Integer week;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "confirmer_id")
    private Admin confirmer;

    @Column
    private String confirmerName;

    @Column
    @Enumerated(EnumType.STRING)
    private WorkStatus status;

    @Column(length = 1000)
    private String message;

    public Work(StudentPartTimeGroup studentPartTimeGroup, PartTime partTime, LocalDateTime startTime, LocalDateTime endTime){
        this.studentPartTimeGroup = studentPartTimeGroup;
        this.student = studentPartTimeGroup.getStudent();
        this.partTimeGroup = studentPartTimeGroup.getPartTimeGroup();
        this.partTime = partTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.week = calculateWeekNumber(startTime);
        this.status = WorkStatus.PENDING;
        updateWorkTime(startTime, endTime);
        updateNightWorkTime(startTime, endTime);
    }

    public Work(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.week = calculateWeekNumber(startTime);
        this.status = WorkStatus.PENDING;
        updateWorkTime(startTime, endTime);
        updateNightWorkTime(startTime, endTime);
    }

    public void approve(Admin approver){
        this.confirmer = approver;
        this.confirmerName = approver.getName();
        this.status = WorkStatus.CONFIRMED;
    }

    public void reject(Admin rejecter, String rejectMessage){
        this.confirmer = rejecter;
        this.confirmerName = rejecter.getName();
        this.status = WorkStatus.REJECTED;
        this.message = rejectMessage;
    }

    public Integer calculateDailyWage(){
        return this.partTime.calculateDailyWage(this);
    }

    public Integer calculateDailyPureWage(){
        return this.partTime.calculateDailyPureWage(this);
    }

    public Integer calculateDailyOvertimeAllowance(){
        return partTime.calculateDailyOvertimeAllowance(this);
    }

    public Integer calculateDailyNightAllowance(){
        return partTime.calculateDailyNightAllowance(this);
    }

    public Float getDayWorkTime(){
        return this.workTime - this.nightWorkTime;
    }

    public boolean isOverWork(){
        return this.workTime > 8;
    }

    public boolean isWeekDay(){
        DayOfWeek dayOfWeek = startTime.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    public Float getOverWorkTime(){
        if(!isOverWork()) return 0F;
        return this.workTime - 8;
    }

    public Integer getYear(){
        return this.startTime.getYear();
    }

    public Integer getMonth(){
        return this.startTime.getMonth().getValue();
    }

    public boolean nonRejected(){
        return this.status != WorkStatus.REJECTED;
    }

    protected void updateWorkTime(LocalDateTime start, LocalDateTime end){
        this.workTime = getTimeDiff(start, end);
    }

    protected void updateNightWorkTime(LocalDateTime startTime, LocalDateTime endTime){
        if(isSameDate(startTime, endTime)){
            this.nightWorkTime = calculateIncludedHours(startTime, endTime);
            return;
        }

        float nightTime = 0F;
        LocalDateTime startTimeEnd = startTime.plusDays(1);
        LocalDateTime endTimeStart = LocalDateTime.of(endTime.getYear(), endTime.getMonth(), endTime.getDayOfMonth(), 0, 0);
        nightTime += getDateDiff(startTimeEnd, endTimeStart) * fullNightWork;
        nightTime += calculateIncludedHours(startTime, startTimeEnd);
        nightTime += calculateIncludedHours(endTimeStart, endTime);
        this.nightWorkTime = nightTime;
    }

    private int calculateWeekNumber(LocalDateTime dateTime) {
        LocalDate localDate = dateTime.toLocalDate();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return localDate.get(weekFields.weekOfMonth());
    }

    private float calculateIncludedHours(LocalDateTime start, LocalDateTime end) {
        LocalDateTime startOfInterval1 = start.with(LocalTime.of(22, 0));
        LocalDateTime endOfInterval1 = start.toLocalDate().plusDays(1).atTime(0, 0);

        LocalDateTime startOfInterval2 = start.toLocalDate().atTime(0, 0);
        LocalDateTime endOfInterval2 = start.with(LocalTime.of(6, 0));

        Float includedDuration1 = calculateIntersection(startOfInterval1, endOfInterval1, start, end);
        Float includedDuration2 = calculateIntersection(startOfInterval2, endOfInterval2, start, end);

        return includedDuration1 + includedDuration2;
    }

    private float calculateIntersection(LocalDateTime b1, LocalDateTime b2, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(b1) || end.isEqual(b1)) return 0F;
        else if (start.isAfter(b2) || start.isEqual(b2)) return 0F;
        else return getTimeDiff(start.isAfter(b1) ? start : b1, end.isBefore(b2) ? end : b2);
    }

    private boolean isSameDate(LocalDateTime start, LocalDateTime end){
        return start.toLocalDate().isEqual(end.toLocalDate());
    }

    private long getDateDiff(LocalDateTime start, LocalDateTime end){
        return Duration.between(start, end).toDays();
    }

    private Float getTimeDiff(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return (float) duration.toMinutes() / 60.0f;
    }

}
