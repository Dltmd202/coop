package org.kt.parttime.parttime.controller;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.common.dto.TimeQuery;
import org.kt.parttime.parttime.dto.PartTimeEditForm;
import org.kt.parttime.parttime.dto.PartTimeForm;
import org.kt.parttime.parttime.dto.PartTimeGroupDetailDto;
import org.kt.parttime.parttime.service.PartTimeService;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/parttimes")
@RequiredArgsConstructor
public class PartTimeController {
    private final PartTimeService partTimeService;

    @GetMapping("/new")
    public String createPartTimeForm(@AuthenticatedUser AuthenticatedUserDto user, Model model){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";

        model.addAttribute("partTimeForm", new PartTimeForm());
        return "parttime/createPartTimeForm";
    }

    @PostMapping("/new")
    public String createPartTime(@AuthenticatedUser AuthenticatedUserDto user, @Valid PartTimeForm form, BindingResult result){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";
        form.validatePartTimeGroup(result);
        if(result.hasErrors()) return "parttime/createPartTimeForm";
        partTimeService.createPartTime(form);

        return "redirect:/parttime-groups";
    }
}
