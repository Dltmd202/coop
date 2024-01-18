package org.kt.parttime.user.service;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.user.dto.AdminEditForm;
import org.kt.parttime.user.entity.Admin;
import org.kt.parttime.user.exception.AlreadyExistsStudentException;
import org.kt.parttime.user.exception.ForbiddenUserException;
import org.kt.parttime.user.repository.AdminRepository;
import org.kt.parttime.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
        if(!admin.getEmail().equals(adminEditForm.getEmail()) && userRepository.existsByEmail(adminEditForm.getEmail()))
            throw new AlreadyExistsStudentException();
        
        admin.update(adminEditForm, passwordEncoder);
    }
}
