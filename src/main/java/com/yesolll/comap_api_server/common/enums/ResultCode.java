package com.yesolll.comap_api_server.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResultCode {

    PROCESS_COMPLETED(OK, "정상처리 되었습니다."),
    CREATE_COMPLETED(CREATED, "정상처리 되었습니다."),
    DELETE_COMPLETED(NO_CONTENT, "정상처리 되었습니다."),

    AUTH_FAILED(UNAUTHORIZED, "사용자 인증에 실패하였습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpCode() {
        return this.httpStatus.value();
    }
}
