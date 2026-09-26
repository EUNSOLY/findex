package com.eunsoly.findex.common.exception.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.event.Level;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ErrorCode {

    // 일반적인 오류 (4xx)
    INVALID_INPUT(Level.WARN, 400, "COMMON_001", "입력값이 올바르지 않습니다"),
    RESOURCE_NOT_FOUND(Level.WARN, 404, "COMMON_002", "요청한 리소스를 찾을 수 없습니다"),

    // 시스템 오류 (5xx)
    DATABASE_ERROR(Level.ERROR, 500, "SYS_001", "데이터베이스 오류가 발생했습니다"),
    EXTERNAL_API_ERROR(Level.ERROR, 500, "SYS_002", "외부 API 호출에 실패했습니다"),
    INTERNAL_SERVER_ERROR(Level.ERROR, 500, "SYS_999", "내부 서버 오류가 발생했습니다"),

    NOT_FOUND_INDEX_INFO(Level.WARN, 404, "INDEX_INFO_001", "지수정보가 존재하지 않습니다."),

    DUPLICATE_INDEX_DATA(Level.WARN, 409, "INDEX_DATA_001", "이미 존재하는 데이터 입니다.");

    Level level;
    int httpStatus;
    String code;
    String defaultMessage;
}
