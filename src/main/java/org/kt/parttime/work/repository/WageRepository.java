package org.kt.parttime.work.repository;

import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.work.entity.Wage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WageRepository extends JpaRepository<Wage, Long> {

    List<Wage> findByPartTimeGroup(PartTimeGroup partTimeGroup);

    @Query("select w from Wage w where w.student = :student and w.partTime = :partTime and w.partTimeGroup = :partTimeGroup and w.year = :year and w.month = :month")
    Optional<Wage> findByStudentAndPartTimeGroupAndPartTimeAndYearAndMonth(
            Student student, PartTimeGroup partTimeGroup, PartTime partTime, Integer year, Integer month);

    @Query("select w from Wage w where w.student = :student and w.partTimeGroup = :partTimeGroup and w.year = :year and w.month = :month")
    List<Wage> findByStudentAndPartTimeGroupAndYearAndMonth(
            Student student, PartTimeGroup partTimeGroup, Integer year, Integer month);

    @Query("select w from Wage w where w.partTimeGroup = :partTimeGroup and w.year = :year and w.month = :month")
    List<Wage> findByPartTimeGroupAndYearAndMonth(PartTimeGroup partTimeGroup, Integer year, Integer month);

    @Query("select w " +
            "from Wage w " +
            "join fetch w.student " +
            "join fetch w.partTimeGroup " +
            "where w.year = :year and w.month = :month " +
            "order by w.partTimeGroup.id")
    List<Wage> findYearAndMonth(Integer year, Integer month);
}
