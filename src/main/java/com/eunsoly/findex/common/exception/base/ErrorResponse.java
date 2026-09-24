package com.eunsoly.findex.common.exception.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {
    private final boolean success;
    private final String message;
    private final Map<String, Object> detail;

    public static ErrorResponse success(String message) {
        return new ErrorResponse(true, message, null);
    }

    public static ErrorResponse fail(String message, Map<String, Object> detail) {
        return new ErrorResponse(false, message, detail);
    }
}
