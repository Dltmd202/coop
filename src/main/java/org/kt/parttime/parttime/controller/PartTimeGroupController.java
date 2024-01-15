package org.kt.parttime.parttime.controller;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.common.dto.TimeQuery;
import org.kt.parttime.parttime.dto.PartTimeEditForm;
import org.kt.parttime.parttime.dto.PartTimeGroupDetailDto;
import org.kt.parttime.parttime.service.PartTimeService;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/parttime-groups")
@RequiredArgsConstructor
public class PartTimeGroupController {
    private final PartTimeService partTimeService;

    // TODO 5원 반올림
    // TODO 마지막날에 초과된 초과근무
    @GetMapping
    public String partTimeList(
            @AuthenticatedUser AuthenticatedUserDto user,
            Pageable pageable,
            Model model
    ){
        if(Objects.isNull(user)) return "redirect:/";

        model.addAttribute("partTimes", partTimeService.partTimes(pageable));
        return "parttime/partTimeList";
    }

    @GetMapping("/{partTimeGroupId}")
    public String partTimeDetail(
            @AuthenticatedUser AuthenticatedUserDto user,
            @PathVariable Long partTimeGroupId,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            Model model
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";
        TimeQuery timeQuery = TimeQuery.of(year, month);

        PartTimeGroupDetailDto partTime = partTimeService.getPartTime(partTimeGroupId, timeQuery);
        model.addAttribute("partTimeGroup", partTime);
        model.addAttribute("time", timeQuery);
        model.addAttribute("prevTime", timeQuery.prev());
        model.addAttribute("time", timeQuery);
        model.addAttribute("nextTime", timeQuery.next());
        model.addAttribute("partTimeGroupId", partTimeGroupId);
        return "parttime/partTimeDetail";
    }

    @GetMapping("/{partTimeGroupId}/parttimes/{partTimeId}/edit")
    public String getUpdatePartTimeForm(
            @AuthenticatedUser AuthenticatedUserDto user,
            @PathVariable Long partTimeGroupId,
            @PathVariable Long partTimeId,
            Model model
    ){
        // TODO
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";

        model.addAttribute("partTimeForm", partTimeService.getPartTimeEditForm(partTimeId));
        model.addAttribute("partTimeId", partTimeId);
        model.addAttribute("partTimeGroupId", partTimeGroupId);
        return "parttime/editPartTimeForm";
    }

    @PostMapping("/{partTimeGroupId}/parttimes/{partTimeId}/edit")
    public String updatePartTime(
            @AuthenticatedUser AuthenticatedUserDto user,
            @PathVariable Long partTimeGroupId,
            @PathVariable Long partTimeId,
            PartTimeEditForm partTimeEditForm
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return "redirect:/";
        partTimeService.updatePartTime(partTimeId, partTimeEditForm);
        return String.format("redirect:/parttime-groups/%d", partTimeGroupId);
    }

    @PostMapping("/{partTimeGroupId}/join")
    public String joinPartTime(
            @AuthenticatedUser AuthenticatedUserDto user,
            @PathVariable Long partTimeGroupId,
            Model model
    ){
        if(Objects.isNull(user) || user.isAdmin()) return "redirect:/";

        partTimeService.joinPartTime(user.getUserId(), partTimeGroupId);
        return "redirect:/";
    }
}
