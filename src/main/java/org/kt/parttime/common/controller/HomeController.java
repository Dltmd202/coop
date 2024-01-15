package org.kt.parttime.common.controller;

import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@AuthenticatedUser AuthenticatedUserDto user){
        if(Objects.isNull(user)) return "redirect:/users/login";
        return "redirect:/users/mypage";
    }
}
