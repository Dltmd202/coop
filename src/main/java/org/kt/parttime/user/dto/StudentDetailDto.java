package org.kt.parttime.user.dto;

import lombok.Getter;
import org.kt.parttime.parttime.dto.PartTimeDto;
import org.kt.parttime.parttime.dto.PartTimeGroupDto;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.entity.Student;

import java.util.List;

@Getter
public class StudentDetailDto extends StudentDto{
    List<PartTimeGroupDto> partTimes;
    public StudentDetailDto(Student user, List<PartTimeGroup> partTimes) {
        super(user);
        this.partTimes = partTimes.stream().map(PartTimeGroupDto::new).toList();
    }
}
