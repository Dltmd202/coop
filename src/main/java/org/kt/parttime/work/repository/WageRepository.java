package org.kt.parttime.work.repository;

import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.work.entity.Wage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WageRepository extends JpaRepository<Wage, Long> {
    Optional<Wage> findByStudentAndPratTimeGroupAndYearAndMonth(Student student, PartTimeGroup partTimeGroup, Integer year, Integer month);

    @Query("select w " +
            "from Wage w " +
            "join fetch w.student " +
            "join fetch w.pratTimeGroup " +
            "where w.year = :year and w.month = :month " +
            "order by w.pratTimeGroup.id")
    List<Wage> findYearAndMonth(Integer year, Integer month);
}
