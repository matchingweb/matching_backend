package com.matching.backend.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON_400", "요청 값이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON_401", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "접근 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON_404", "요청한 리소스를 찾을 수 없습니다."),
    CONFLICT(HttpStatus.CONFLICT, "COMMON_409", "이미 존재하거나 충돌하는 데이터입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "USER_409_1", "이미 사용 중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404_1", "사용자를 찾을 수 없습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "TEAM_404_1", "팀을 찾을 수 없습니다."),
    TEAM_FORBIDDEN(HttpStatus.FORBIDDEN, "TEAM_403_1", "팀을 수정할 권한이 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_404_1", "게시글을 찾을 수 없습니다."),
    POST_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_403_1", "게시글을 수정할 권한이 없습니다."),
    POST_TEAM_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_403_2", "해당 팀으로 게시글을 작성할 권한이 없습니다."),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "AUTH_401_1", "이메일 또는 비밀번호가 올바르지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_401_2", "유효하지 않은 인증 토큰입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
