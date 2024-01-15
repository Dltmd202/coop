package org.kt.parttime.user.controller;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.user.dto.AdminEditForm;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.kt.parttime.user.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/edit")
    public String getAdminEditForm(
            @AuthenticatedUser AuthenticatedUserDto user,
            Model model
    ){
        if(Objects.isNull(user)) return "redirect:/";
        model.addAttribute("adminForm", adminService.getAdminEditForm(user.getUserId()));
        return "admin/editAdminForm";
    }

    @PostMapping("/edit")
    public String editAdmin(
            @Valid @ModelAttribute("adminForm") AdminEditForm adminForm,
            BindingResult result,
            @AuthenticatedUser AuthenticatedUserDto user
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";
        adminForm.isValidPassword(result);
        if(result.hasErrors()) return "admin/editAdminForm";

        adminService.editAdmin(user.getUserId(), adminForm);
        return "redirect:/users/mypage";
    }
}
