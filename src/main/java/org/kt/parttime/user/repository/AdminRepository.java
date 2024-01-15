package org.kt.parttime.user.repository;

import org.kt.parttime.user.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
