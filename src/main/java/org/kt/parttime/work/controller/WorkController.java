package org.kt.parttime.work.controller;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.common.dto.TimeQuery;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.kt.parttime.work.dto.WorkDetailPageDto;
import org.kt.parttime.work.dto.WorkDto;
import org.kt.parttime.work.dto.WorkForm;
import org.kt.parttime.work.service.WorkService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/parttime-groups/{partTimeGroupId}")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;

    @GetMapping("/works")
    public String studentWorkPage(
            @AuthenticatedUser AuthenticatedUserDto user,
            @PathVariable Long partTimeGroupId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model
    ){
        if(Objects.isNull(user)) return "redirect:/";
        TimeQuery timeQuery = TimeQuery.of(year, month);

        WorkDetailPageDto workDetail = workService.getWorkDetail(partTimeGroupId, user.getUserId(), timeQuery);
        model.addAttribute("monthlyWork", workDetail.getMonthlyWork());
        model.addAttribute("student", workDetail.getStudent());
        model.addAttribute("workForm", new WorkForm(year, month));
        model.addAttribute("partTimeGroup", workDetail.getPartTimeGroup());
        model.addAttribute("partTimes", workDetail.getPartTimes());
        model.addAttribute("time", timeQuery);
        model.addAttribute("prevTime", timeQuery.prev());
        model.addAttribute("nextTime", timeQuery.next());
        model.addAttribute("studentId", user.getUserId());
        return "work/workDetail";
    }

    @GetMapping("/students/{studentId}/works")
    public String adminWorkPage(
            @AuthenticatedUser AuthenticatedUserDto user,
            @PathVariable Long studentId,
            @PathVariable Long partTimeGroupId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";
        TimeQuery timeQuery = TimeQuery.of(year, month);

        WorkDetailPageDto workDetail = workService.getWorkDetail(partTimeGroupId, studentId, timeQuery);
        model.addAttribute("monthlyWork", workDetail.getMonthlyWork());
        model.addAttribute("student", workDetail.getStudent());
        model.addAttribute("workForm", new WorkForm(year, month));
        model.addAttribute("partTimeGroup", workDetail.getPartTimeGroup());
        model.addAttribute("partTimes", workDetail.getPartTimes());
        model.addAttribute("time", timeQuery);
        model.addAttribute("prevTime", timeQuery.prev());
        model.addAttribute("nextTime", timeQuery.next());
        model.addAttribute("studentId", studentId);
        return "work/workDetail";
    }

    @PostMapping("/works")
    public String registerWork(
            @PathVariable Long partTimeGroupId,
            @AuthenticatedUser AuthenticatedUserDto user,
            @Valid WorkForm workForm,
            BindingResult result
    ){
        if(Objects.isNull(user)) return "redirect:/";
        workForm.validateTime(result);
        if(result.hasErrors()) return "/work/workDetail";

        WorkDto work = workService.registerWork(partTimeGroupId, workForm.getPartTimeId(), user.getUserId(), workForm.getStartTime(), workForm.getEndTime());

        return String.format(
                "redirect:/parttime-groups/%d/works?year=%d&month=%d",
                partTimeGroupId, work.getYear(), work.getMonth()
        );
    }

    @PostMapping("/students/{studentId}/works")
    public String registerWorkByAdmin(
            @PathVariable Long partTimeGroupId,
            @PathVariable Long studentId,
            @AuthenticatedUser AuthenticatedUserDto user,
            @Valid WorkForm workForm,
            BindingResult result
    ){
        if(Objects.isNull(user)) return "redirect:/";
        if(result.hasErrors())
            return String.format(
                "redirect:/parttime-groups/%d/students/%d/works?year=%d&month=%d",
                partTimeGroupId, studentId, workForm.getYear(), workForm.getMonth()
        );

        workService.registerWork(partTimeGroupId, workForm.getPartTimeId(), studentId, workForm.getStartTime(), workForm.getEndTime());
        return String.format(
                "redirect:/parttime-groups/%d/students/%d/works?year=%d&month=%d",
                partTimeGroupId, studentId, workForm.getYear(), workForm.getMonth()
        );
    }

    @PostMapping("/works/{workId}/delete")
    public String deleteWork(
            @PathVariable Long partTimeGroupId,
            @PathVariable Long workId,
            @AuthenticatedUser AuthenticatedUserDto user
    ) {
        if(Objects.isNull(user)) return "redirect:/";

        WorkDto workDto = workService.deleteWork(partTimeGroupId, workId, user.getUserId());
        return String.format(
                "redirect:/parttime-groups/%d/works?year=%d&month=%d",
                partTimeGroupId, workDto.getYear(), workDto.getMonth()
        );
    }

    @PostMapping("/students/{studentId}/works/{workId}/delete")
    public String deleteWorkByAdmin(
            @PathVariable Long partTimeGroupId,
            @PathVariable Long workId,
            @PathVariable Long studentId,
            @AuthenticatedUser AuthenticatedUserDto user
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";

        WorkDto workDto = workService.deleteWork(partTimeGroupId, workId, studentId);
        return String.format(
                "redirect:/parttime-groups/%d/students/%d/works?year=%d&month=%d",
                partTimeGroupId, studentId, workDto.getYear(), workDto.getMonth());
    }

    @PostMapping("/students/{studentId}/works/{workId}/approve")
    public String approveWorkByAdmin(
            @PathVariable Long partTimeGroupId,
            @PathVariable Long workId,
            @PathVariable Long studentId,
            @AuthenticatedUser AuthenticatedUserDto user
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";


        WorkDto workDto = workService.approveWork(partTimeGroupId, user.getUserId(), workId, studentId);
        return String.format(
                "redirect:/parttime-groups/%d/students/%d/works?year=%d&month=%d",
                partTimeGroupId, studentId, workDto.getYear(), workDto.getMonth());
    }

    @PostMapping("/students/{studentId}/works/{workId}/reject")
    public String rejectWorkByAdmin(
            @PathVariable Long partTimeGroupId,
            @PathVariable Long workId,
            @PathVariable Long studentId,
            @AuthenticatedUser AuthenticatedUserDto user,
            @RequestParam String rejectMessage
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";

        WorkDto workDto = workService.rejectWork(partTimeGroupId, user.getUserId(), workId, studentId, rejectMessage);
        return String.format(
                "redirect:/parttime-groups/%d/students/%d/works?year=%d&month=%d",
                partTimeGroupId, studentId, workDto.getYear(), workDto.getMonth());
    }


}
