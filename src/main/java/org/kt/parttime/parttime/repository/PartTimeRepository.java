package org.kt.parttime.parttime.repository;

import com.querydsl.core.annotations.QueryInit;
import org.kt.parttime.parttime.entity.PartTime;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.work.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartTimeRepository extends JpaRepository<PartTime, Long> {
    @Query("select pt from PartTime pt join fetch pt.partTimeGroup where pt.id = :id")
    Optional<PartTime> findByIdWithPartTimeGroup(Long id);
}
