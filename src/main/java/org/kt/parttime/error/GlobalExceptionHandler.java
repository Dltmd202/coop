package org.kt.parttime.error;

import lombok.extern.slf4j.Slf4j;
import org.kt.parttime.parttime.exception.InvalidPartTimeJoinException;
import org.kt.parttime.user.exception.InvalidAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ModelAndView ex(BusinessException e){
        return new ModelAndView(e.getErrorView(), e.toModel());
    }

    @ExceptionHandler({InvalidAuthenticationException.class})
    public ModelAndView ex(InvalidAuthenticationException e){
        return new ModelAndView("error/4xx", e.toModel());
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView ex(RuntimeException e){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "서버 에러입니다.");
        log.error("{}", e);
        e.printStackTrace();
        return new ModelAndView("error/5xx", map);
    }
}
