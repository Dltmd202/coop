package org.kt.parttime.user.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidAuthenticationException extends BusinessException {
    public InvalidAuthenticationException() {
        super(HttpStatus.UNAUTHORIZED, "잘못된 인증입니다.");
    }
}
