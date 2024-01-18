package org.kt.parttime.user.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsStudentException extends BusinessException {
    public AlreadyExistsStudentException() {
        super(HttpStatus.BAD_REQUEST, "가입할 수 없는 요청입니다.");
    }
}
