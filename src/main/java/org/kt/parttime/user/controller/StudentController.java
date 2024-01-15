package org.kt.parttime.user.controller;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.kt.parttime.user.dto.StudentForm;
import org.kt.parttime.user.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/new")
    public String createForm(@AuthenticatedUser AuthenticatedUserDto user, Model model){
        if(Objects.nonNull(user)) return "redirect:/";

        model.addAttribute("studentForm", new StudentForm());
        return "users/createStudentForm";
    }

    @GetMapping("/edit")
    public String editStudentForm(@AuthenticatedUser AuthenticatedUserDto user, Model model){
        if(Objects.isNull(user)) return "redirect:/";

        model.addAttribute("studentForm", new StudentForm(studentService.getStudentDto(user.getUserId())));
        return "users/createStudentForm";
    }

    @PostMapping("/edit")
    public String editStudent(
            @AuthenticatedUser AuthenticatedUserDto user,
            @Valid StudentForm studentForm,
            BindingResult result,
            Model model
    ){
        if(Objects.isNull(user)) return "redirect:/";
        studentForm.validatePassword(result);
        if(result.hasErrors()) return "users/createStudentForm";

        studentService.editStudent(user.getUserId(), studentForm);
        return "redirect:/users/mypage";
    }

    @PostMapping("/new")
    public String create(@Valid StudentForm form, BindingResult result, Model model){
        form.validatePassword(result);

        if(result.hasErrors()) return "users/createStudentForm";

        studentService.studentJoin(form);

        return "redirect:/";
    }
}
