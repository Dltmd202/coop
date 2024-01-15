package org.kt.parttime.parttime.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidPartTimeJoinException extends BusinessException {
    public InvalidPartTimeJoinException() {
        super(HttpStatus.BAD_REQUEST, "잘못된 근로장학 신청입니다.");
    }
}
