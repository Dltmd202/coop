package org.kt.parttime.user.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class ForbiddenUserException extends BusinessException {
    public ForbiddenUserException() {
        super(HttpStatus.FORBIDDEN, "접근할 수 없는 사용자입니다.");
    }
}
