package org.kt.parttime.parttime.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class DuplicatedPartTimeJoinException extends BusinessException {
    public DuplicatedPartTimeJoinException() {
        super(HttpStatus.BAD_REQUEST, "이미 가입된 근로장학입니다.");
    }
}
