package org.kt.parttime.user.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class NotFoundStudentException extends BusinessException {
    public NotFoundStudentException(){
        super(HttpStatus.NOT_FOUND, "찾을 수 없는 사용자입니다.");
    }
}
