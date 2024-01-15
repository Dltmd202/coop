package org.kt.parttime.parttime.repository;

import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartTimeGroupRepository extends JpaRepository<PartTimeGroup, Long> {
    List<PartTimeGroup> findByYearAndSemester(Integer year, Integer semester);

    Page<PartTimeGroup> findAllByOrderByIdDesc(Pageable pageable);

    @Query("select ptg from PartTimeGroup ptg inner join StudentPartTimeGroup sptg on sptg.partTimeGroup = ptg and sptg.student = :student")
    List<PartTimeGroup> findByPartTimeGroupByStudentId(Student student);

    @Query("select case when count(ptg) > 0 then true else false end from PartTimeGroup ptg inner join StudentPartTimeGroup sptg on sptg.partTimeGroup = ptg and sptg.partTimeGroup.id = :partTimeGroupId and sptg.student.id = :studentId")
    boolean existsByPartTimeGroupIdAndStudentId(Long partTimeGroupId, Long studentId);

    @Query("select ptg from PartTimeGroup ptg inner join fetch StudentPartTimeGroup sptg on sptg.partTimeGroup = ptg and sptg.partTimeGroup.id = :partTimeGroupId and sptg.student.id = :studentId")
    Optional<PartTimeGroup> findByPartTimeGroupIdAndStudentId(Long partTimeGroupId, Long studentId);
}
