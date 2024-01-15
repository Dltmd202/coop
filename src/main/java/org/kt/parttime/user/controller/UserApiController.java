package org.kt.parttime.user.controller;

import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import org.kt.parttime.user.service.StudentService;
import org.kt.parttime.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserApiController {
    private final StudentService studentService;
    private final UserService userService;

    /**
     * 중복된 이메일 조회
     * @param email
     */
    @GetMapping("/email")
    public ResponseEntity<Boolean> isValidEmail(
            @RequestParam(name = "email") String email
    ){
        return ResponseEntity
                .status(200)
                .body(
                        userService.isValidEmail(email)
                );
    }

    /**
     *  중복된 학번 조회
     * @param studentId
     */
    @GetMapping("/studentId")
    public ResponseEntity<Boolean> isValidStudentId(
            @RequestParam(name = "studentId") String studentId
    ){
        return ResponseEntity
                .status(200)
                .body(
                        studentService.isValidStudentId(studentId)
                );
    }
}
