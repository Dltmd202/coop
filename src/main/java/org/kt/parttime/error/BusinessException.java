package org.kt.parttime.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public class BusinessException extends RuntimeException{
    private final HttpStatus status;

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public Map<String, String> toModel(){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", getMessage());
        return map;
    }

    public String getErrorView(){
        int type = status.value() / 100;
        if(0 >= type || type >= 6) type = 5;
        return String.format("error/%dxx", type);
    }
}
