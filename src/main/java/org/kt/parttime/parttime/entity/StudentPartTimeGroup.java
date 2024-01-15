package org.kt.parttime.parttime.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kt.parttime.common.entity.BaseTimeEntity;
import org.kt.parttime.user.entity.Student;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentPartTimeGroup extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "part_time_group_id")
    private PartTimeGroup partTimeGroup;

    public StudentPartTimeGroup(Student student, PartTimeGroup partTimeGroup) {
        this.student = student;
        this.partTimeGroup = partTimeGroup;
    }
}
