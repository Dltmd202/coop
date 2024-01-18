package org.kt.parttime.user.repository;

import org.kt.parttime.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentId(String studentId);

    boolean existsByStudentIdOrEmail(String studentId, String email);

    @Query("select sptg.student from StudentPartTimeGroup sptg where sptg.partTimeGroup.id = :partTimeGroupId")
    List<Student> findStudentByPartTimeGroupId(Long partTimeGroupId);

}
