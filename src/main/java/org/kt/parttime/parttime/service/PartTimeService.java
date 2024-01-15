package org.kt.parttime.parttime.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kt.parttime.common.dto.TimeQuery;
import org.kt.parttime.parttime.dto.*;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.parttime.exception.DuplicatedPartTimeJoinException;
import org.kt.parttime.parttime.exception.InvalidPartTimeJoinException;
import org.kt.parttime.parttime.exception.NotFoundPartTimeException;
import org.kt.parttime.parttime.repository.PartTimeGroupRepository;
import org.kt.parttime.parttime.repository.PartTimeRepository;
import org.kt.parttime.user.dto.StudentWageDto;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.user.repository.StudentRepository;
import org.kt.parttime.utils.WageExcelUtils;
import org.kt.parttime.work.entity.Wage;
import org.kt.parttime.work.repository.WageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class PartTimeService {
    private final PartTimeRepository partTimeRepository;
    private final StudentRepository studentRepository;
    private final WageRepository wageRepository;
    private final PartTimeGroupRepository partTimeGroupRepository;

    @Transactional
    public void createPartTime(PartTimeForm partTimeForm){
        PartTimeGroup partTimeGroup = extractPartTimeGroupFromPartTimeForm(partTimeForm);
        PartTime partTime = partTimeForm.makePartTime();
        partTimeGroup.joinPartTime(partTime);
        partTimeGroupRepository.save(partTimeGroup);
    }

    @Transactional
    public void joinPartTime(Long userId, Long partTimeGroupId){
        this.validateUniquePartTimeUser(userId, partTimeGroupId);
        Student user = studentRepository.findById(userId)
                .orElseThrow(InvalidPartTimeJoinException::new);
        PartTimeGroup partTimeGroup = partTimeGroupRepository.findById(partTimeGroupId)
                .orElseThrow(InvalidPartTimeJoinException::new);
        partTimeGroupRepository.findByPartTimeGroupIdAndStudentId(partTimeGroupId, userId);

        partTimeGroup.join(user);
    }

    @Transactional(readOnly = true)
    public Page<PartTimeGroupBriefDto> partTimes(Pageable pageable){
        return partTimeGroupRepository.findAllByOrderByIdDesc(pageable)
                .map(PartTimeGroupBriefDto::new);
    }

    @Transactional(readOnly = true)
    public PartTimeEditForm getPartTimeEditForm(Long partTimeId){
        PartTime partTime = partTimeRepository.findById(partTimeId)
                .orElseThrow(NotFoundPartTimeException::new);

        return new PartTimeEditForm(partTime);
    }

    @Transactional
    public void updatePartTime(Long partTimeId, PartTimeEditForm partTimeEditForm) {
        PartTime partTime = partTimeRepository.findByIdWithPartTimeGroup(partTimeId)
                .orElseThrow(NotFoundPartTimeException::new);

        // TODO 근장 수정되면 급여 리프레시
        partTime.update(partTimeEditForm);
    }

    @Transactional(readOnly = true)
    public PartTimeGroupDetailDto getPartTime(Long partTimeGroupId, TimeQuery timeQuery){
        PartTimeGroup partTimeGroup = partTimeGroupRepository.findById(partTimeGroupId)
                .orElseThrow(NotFoundPartTimeException::new);

        List<Student> students = studentRepository.findStudentByPartTimeGroupId(partTimeGroupId);
        List<StudentWageDto> wageStudents = students.stream()
                .map(s -> {
                    Wage wage = wageRepository.findByStudentAndPratTimeGroupAndYearAndMonth(s, partTimeGroup, timeQuery.getYear(), timeQuery.getMonth())
                            .orElseGet(() -> new Wage(s, partTimeGroup, timeQuery.getYear(), timeQuery.getMonth()));
                    return new StudentWageDto(s, wage.getMonthlyWage());
                })
                .toList();
        return new PartTimeGroupDetailDto(partTimeGroup, wageStudents);
    }

    @Transactional(readOnly = true)
    public List<PartTimeGroupDto> getPartTimeGroupList(Integer year, Integer semester){
        List<PartTimeGroup> partTimeGroupList = partTimeGroupRepository.findByYearAndSemester(year, semester);
        return partTimeGroupList.stream().map(PartTimeGroupDto::new).toList();
    }

    private void validateUniquePartTimeUser(Long userId, Long partTimeGroupId){
        if(partTimeGroupRepository.existsByPartTimeGroupIdAndStudentId(partTimeGroupId, userId))
            throw new DuplicatedPartTimeJoinException();
    }

    private PartTimeGroup extractPartTimeGroupFromPartTimeForm(PartTimeForm partTimeForm) {
        if(partTimeForm.isValidCreateGroup())
            return partTimeGroupRepository.save(partTimeForm.makePartTimeGroup());
        else if(partTimeForm.isValidSelectGroup())
            return partTimeGroupRepository.findById(partTimeForm.getGroupSelect())
                    .orElseThrow(IllegalAccessError::new);
        throw new InvalidPartTimeJoinException();
    }


}
