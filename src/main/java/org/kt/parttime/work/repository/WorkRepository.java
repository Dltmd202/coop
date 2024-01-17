package org.kt.parttime.work.repository;

import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.parttime.entity.StudentPartTimeGroup;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.work.entity.Work;
import org.kt.parttime.work.entity.WorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {

    List<Work> findAllByPartTimeGroup(PartTimeGroup partTimeGroup);

    @Modifying
    @Query("update Work w set w.confirmer = null, w.status = 'PENDING', w.confirmerName = null where w.partTimeGroup = :partTimeGroup")
    void vacateStatusAndConfirm(PartTimeGroup partTimeGroup);

    @Query("select w " +
            "from Work w " +
            "join fetch w.partTime " +
            "where w.studentPartTimeGroup = :studentPartTimeGroup " +
            "and w.startTime >= :start and w.startTime < :end " +
            "order by w.startTime")
    List<Work> findAllByStudentPartTimeGroup(StudentPartTimeGroup studentPartTimeGroup, LocalDateTime start, LocalDateTime end);

    @Query("select w " +
            "from Work w " +
            "join fetch w.partTime " +
            "where w.partTimeGroup = :partTimeGroup and w.student = :student " +
            "and w.startTime >= :start and w.startTime < :end " +
            "and w.status = 'CONFIRMED'" +
            "order by w.startTime")
    List<Work> findAllByStudentAndPartTimeGroup(PartTimeGroup partTimeGroup, Student student, LocalDateTime start, LocalDateTime end);

    @Query("select w " +
            "from Work w " +
            "join fetch w.student " +
            "join fetch w.partTimeGroup " +
            "join fetch w.partTime " +
            "where w.partTimeGroup.id = :partTimeGroupId " +
            "and w.student.id = :studentId " +
            "and w.id = :workId")
    Optional<Work> findByPartTimeGroupAndStudentIdWithStudentAndPartTimeGroup(Long partTimeGroupId, Long studentId, Long workId);

}
