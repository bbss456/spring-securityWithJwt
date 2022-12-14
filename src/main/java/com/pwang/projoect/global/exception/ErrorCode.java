package com.pwang.projoect.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    ENTITY_NOT_FOUND(400, "C001", " Entity Not Found"),

    INVALID_TYPE_VALUE(400, "C002", " Invalid Type Value"),

    UNAUTHORIZEDException (401, "C003", "계정 인증이 실패하였습니다."),

    NULLTokenException (401, "C004", "토큰이 존재하지 않습니다."),

    ExpiredJwtException(401, "C005", "기존 토큰이 만료되었습니다. refreshToken가지고 토큰을 재발급 받으세요. "),

    ReLogin(401, "C006", "refreshToken 토큰이 만료되었습니다."),

    ForbiddenException(403, "C007", "해당 요청에 대한 권한이 없습니다.");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
