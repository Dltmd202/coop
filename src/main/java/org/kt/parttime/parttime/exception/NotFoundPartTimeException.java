package org.kt.parttime.parttime.exception;

import org.kt.parttime.error.BusinessException;
import org.springframework.http.HttpStatus;

public class NotFoundPartTimeException extends BusinessException {
    public NotFoundPartTimeException() {
        super(HttpStatus.NOT_FOUND, "찾을 수 없는 근로장학입니다.");
    }
}
