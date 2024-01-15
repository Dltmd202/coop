package org.kt.parttime.work.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class ForbiddenWorkException extends BusinessException {
    public ForbiddenWorkException() {
        super(HttpStatus.FORBIDDEN, "접근할 수 없는 근무일지입니다.");
    }
}
