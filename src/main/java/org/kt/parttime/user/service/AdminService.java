package org.kt.parttime.user.service;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.user.dto.AdminEditForm;
import org.kt.parttime.user.entity.Admin;
import org.kt.parttime.user.exception.ForbiddenUserException;
import org.kt.parttime.user.repository.AdminRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public AdminEditForm getAdminEditForm(Long userId) {
        Admin admin = adminRepository.findById(userId)
                .orElseThrow(ForbiddenUserException::new);
        return AdminEditForm.from(admin);
    }

    @Transactional
    public void editAdmin(Long adminId, AdminEditForm adminEditForm) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(ForbiddenUserException::new);
        admin.update(adminEditForm, passwordEncoder);
    }
}
