package org.kt.parttime.work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kt.parttime.parttime.dto.PartTimeBriefDto;
import org.kt.parttime.parttime.dto.PartTimeGroupDto;
import org.kt.parttime.parttime.entity.StudentPartTimeGroup;
import org.kt.parttime.user.dto.StudentDto;
import org.kt.parttime.work.entity.Work;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkDetailPageDto {
    private MonthlyWorkDto monthlyWork;
    private PartTimeGroupDto partTimeGroup;
    private StudentDto student;
    private List<PartTimeBriefDto> partTimes;

    public WorkDetailPageDto(StudentPartTimeGroup studentPartTimeGroup, List<Work> works){
        this.monthlyWork = new MonthlyWorkDto(works);
        this.partTimeGroup = new PartTimeGroupDto(studentPartTimeGroup.getPartTimeGroup());
        this.student = new StudentDto(studentPartTimeGroup.getStudent());
        this.partTimes = studentPartTimeGroup.getPartTimeGroup()
                .getPartTimes().stream()
                .map(PartTimeBriefDto::new)
                .toList();
    }
}
