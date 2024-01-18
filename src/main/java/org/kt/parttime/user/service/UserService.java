package org.kt.parttime.user.service;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.parttime.entity.PartTimeGroup;
import org.kt.parttime.parttime.repository.PartTimeGroupRepository;
import org.kt.parttime.user.dto.*;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.user.entity.User;
import org.kt.parttime.user.entity.UserRole;
import org.kt.parttime.user.exception.ForbiddenUserException;
import org.kt.parttime.user.exception.InvalidAuthenticationException;
import org.kt.parttime.user.repository.AdminRepository;
import org.kt.parttime.user.repository.StudentRepository;
import org.kt.parttime.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final PartTimeGroupRepository partTimeGroupRepository;

    @Transactional(readOnly = true)
    public boolean isValidEmail(String email){
        return !userRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public AuthenticatedUserDto login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidAuthenticationException::new);

        if(user.login(passwordEncoder, password)) throw new InvalidAuthenticationException();
        return new AuthenticatedUserDto(user);
    }

    @Transactional(readOnly = true)
    public UserDto mypage(Long userId, UserRole role){
        return getUserDto(userId, role);
    }

    private UserDto getUserDto(Long userId, UserRole role) {
        if(role == UserRole.ADMIN){
            return new AdminDto(
                    adminRepository.findById(userId)
                    .orElseThrow(ForbiddenUserException::new)
            );
        }

        else {
            Student user = studentRepository.findById(userId)
                    .orElseThrow(ForbiddenUserException::new);
            List<PartTimeGroup> partTimes = partTimeGroupRepository.findByPartTimeGroupByStudentId(user);
            return new StudentDetailDto(user, partTimes);
        }
    }
}
