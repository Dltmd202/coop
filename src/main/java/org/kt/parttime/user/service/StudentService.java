package org.kt.parttime.user.service;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.user.dto.StudentDto;
import org.kt.parttime.user.dto.StudentForm;
import org.kt.parttime.user.entity.Student;
import org.kt.parttime.user.exception.AlreadyExistsStudentException;
import org.kt.parttime.user.exception.NotFoundStudentException;
import org.kt.parttime.user.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long studentJoin(StudentForm studentForm){
        if(studentRepository.existsByStudentIdOrEmail(studentForm.getStudentId(), studentForm.getEmail()))
            throw new AlreadyExistsStudentException();
        Student student = studentForm.toModel();
        student.signup(passwordEncoder);
        studentRepository.save(student);
        return student.getId();
    }

    @Transactional(readOnly = true)
    public Boolean isValidStudentId(String studentId) {
        return !studentRepository.existsByStudentId(studentId);
    }

    @Transactional(readOnly = true)
    public StudentDto getStudentDto(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(NotFoundStudentException::new);
        return new StudentDto(student);
    }

    @Transactional
    public void editStudent(Long userId, StudentForm studentForm) {
        Student student = validateEditStudent(userId, studentForm);
        student.update(studentForm);
    }

    private Student validateEditStudent(Long userId, StudentForm studentForm){
        Student student = studentRepository.findById(userId).orElseThrow(NotFoundStudentException::new);

        if(studentForm.getEmail().equals(student.getEmail()) && studentForm.getStudentId().equals(student.getStudentId()))
            return student;
        if(studentRepository.existsByStudentIdOrEmail(studentForm.getStudentId(), studentForm.getEmail()))
            throw new AlreadyExistsStudentException();
        return student;
    }
}
