package org.kt.parttime.user.controller;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.common.constants.SessionConst;
import org.kt.parttime.user.dto.AdminDto;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.kt.parttime.user.dto.StudentLoginForm;
import org.kt.parttime.user.dto.UserDto;
import org.kt.parttime.user.entity.UserRole;
import org.kt.parttime.user.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String createLoginForm(@AuthenticatedUser AuthenticatedUserDto userId, Model model){
        if(Objects.nonNull(userId)) return "redirect:/";

        model.addAttribute("studentLoginForm", new StudentLoginForm());
        return "users/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid StudentLoginForm form, BindingResult result, HttpServletRequest request){
        if(result.hasErrors()) return "users/loginForm";

        AuthenticatedUserDto authenticatedUser = userService.login(form.getEmail(), form.getPassword());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, authenticatedUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(@AuthenticatedUser AuthenticatedUserDto user, HttpServletRequest request){
        if(Objects.isNull(user)) return "redirect:/";
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(@AuthenticatedUser AuthenticatedUserDto user, Model model) {
        if (Objects.isNull(user)) return "redirect:/";
        UserDto userDto = userService.mypage(user.getUserId(), UserRole.valueOf(user.getRole()));
        model.addAttribute("user", userDto);
        return userDto.getViewName();
    }

}
