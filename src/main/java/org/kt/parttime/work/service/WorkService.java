package org.kt.parttime.work.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.kt.parttime.common.dto.TimeQuery;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.parttime.entity.StudentPartTimeGroup;
import org.kt.parttime.parttime.repository.PartTimeGroupRepository;
import org.kt.parttime.parttime.repository.PartTimeRepository;
import org.kt.parttime.parttime.repository.StudentPartTimeGroupRepository;
import org.kt.parttime.user.dto.StudentWageDetailDto;
import org.kt.parttime.user.dto.StudentWageDto;
import org.kt.parttime.user.entity.Admin;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.user.repository.AdminRepository;
import org.kt.parttime.user.repository.StudentRepository;
import org.kt.parttime.utils.WageExcelUtils;
import org.kt.parttime.work.dto.WorkDetailPageDto;
import org.kt.parttime.work.dto.WorkDto;
import org.kt.parttime.work.entity.Wage;
import org.kt.parttime.work.entity.Work;
import org.kt.parttime.work.exception.ForbiddenWorkException;
import org.kt.parttime.work.repository.WageRepository;
import org.kt.parttime.work.repository.WorkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final WorkRepository workRepository;
    private final PartTimeRepository partTimeRepository;
    private final StudentPartTimeGroupRepository studentPartTimeGroupRepository;
    private final AdminRepository adminRepository;
    private final WageRepository wageRepository;

    @Transactional(readOnly = true)
    public WorkDetailPageDto getWorkDetail(Long partTimeGroupId, Long studentId, TimeQuery timeQuery){
        StudentPartTimeGroup studentPartTimeGroup = studentPartTimeGroupRepository
                .findByStudentIdAndPartTimeGroupId(studentId, partTimeGroupId)
                .orElseThrow(ForbiddenWorkException::new);

        List<Work> works = workRepository.findAllByStudentPartTimeGroup(
                studentPartTimeGroup, timeQuery.thisLocalDateTime(), timeQuery.nextLocalDateTime());

        return new WorkDetailPageDto(studentPartTimeGroup, works);
    }

    @Transactional
    public WorkDto registerWork(Long partTimeGroupId, Long partTimeId, Long userId, LocalDateTime start, LocalDateTime end){
        StudentPartTimeGroup studentPartTime =
                studentPartTimeGroupRepository.findByStudentIdAndPartTimeGroupId(userId, partTimeGroupId)
                .orElseThrow(ForbiddenWorkException::new);
        PartTime partTime = partTimeRepository.findById(partTimeId)
                .orElseThrow(ForbiddenWorkException::new);

        Work work = new Work(studentPartTime, partTime, start, end);
        workRepository.save(work);
        return new WorkDto(work);
    }

    @Transactional
    public WorkDto deleteWork(Long partTimeGroupId, Long workId, Long userId) {
        Work work = workRepository.findByPartTimeGroupAndStudentIdWithStudentAndPartTimeGroup(partTimeGroupId, userId, workId)
                .orElseThrow(ForbiddenWorkException::new);
        workRepository.delete(work);
        updateWage(work, work.getStudent(), work.getPartTimeGroup());
        return new WorkDto(work);
    }

    @Transactional
    public WorkDto approveWork(Long partTimeGroupId, Long approverId, Long workId, Long studentId) {
        Admin admin = adminRepository.findById(approverId)
                .orElseThrow(ForbiddenWorkException::new);
        Work work = workRepository.findByPartTimeGroupAndStudentIdWithStudentAndPartTimeGroup(partTimeGroupId, studentId, workId)
                .orElseThrow(ForbiddenWorkException::new);
        work.approve(admin);
        workRepository.save(work);
        updateWage(work, work.getStudent(), work.getPartTimeGroup());
        return new WorkDto(work);
    }

    @Transactional
    public WorkDto rejectWork(Long partTimeGroupId, Long rejectorId, Long workId, Long studentId) {
        Admin admin = adminRepository.findById(rejectorId)
                .orElseThrow(ForbiddenWorkException::new);
        Work work = workRepository.findByPartTimeGroupAndStudentIdWithStudentAndPartTimeGroup(partTimeGroupId, studentId, workId)
                .orElseThrow(ForbiddenWorkException::new);
        work.reject(admin);
        workRepository.save(work);
        updateWage(work, work.getStudent(), work.getPartTimeGroup());
        return new WorkDto(work);
    }

    private void updateWage(Work work, Student student, PartTimeGroup partTimeGroup){
        TimeQuery time = TimeQuery.of(work.getYear(), work.getMonth());
        Wage wage = wageRepository.findByStudentAndPratTimeGroupAndYearAndMonth(student, partTimeGroup, work.getYear(), work.getMonth())
                .orElseGet(() -> new Wage(student, partTimeGroup, work.getYear(), work.getMonth()));
        List<Work> weeklyWorks = workRepository.findAllByStudentPartTimeGroupAndNotRejected(
                partTimeGroup,
                student,
                time.thisLocalDateTimeFirstDayOfWeek(work.getWeek()),
                time.thisLocalDateTimeLastDayOfWeek(work.getWeek())
        );

        int weeklyWage = weeklyWorks.stream()
                .map(Work::calculateDailyWage)
                .mapToInt(Integer::intValue)
                .sum();
        weeklyWage += partTimeGroup.calculateHolidayAllowance(weeklyWorks);
        wage.updateWage(work.getWeek(), weeklyWage);
        wageRepository.save(wage);
    }

    @Transactional(readOnly = true)
    public Workbook makeExcel(TimeQuery timeQuery){
        List<StudentWageDetailDto> studentWage = wageRepository
                .findYearAndMonth(timeQuery.getYear(), timeQuery.getMonth())
                .stream().map(w -> new StudentWageDetailDto(w.getPratTimeGroup(), w.getStudent(), w.getMonthlyWage()))
                .toList();
        return WageExcelUtils.makeExcel(studentWage);
    }


}
