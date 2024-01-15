package org.kt.parttime.parttime.repository;

import org.kt.parttime.parttime.entity.StudentPartTimeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentPartTimeGroupRepository extends JpaRepository<StudentPartTimeGroup, Long> {

    @Query("select sptg " +
            "from StudentPartTimeGroup sptg " +
            "join fetch sptg.partTimeGroup " +
            "join fetch sptg.student " +
            "where sptg.student.id = :studentId " +
            "and sptg.partTimeGroup.id = :partTimeGroupId")
    Optional<StudentPartTimeGroup> findByStudentIdAndPartTimeGroupId(Long studentId, Long partTimeGroupId);
}
