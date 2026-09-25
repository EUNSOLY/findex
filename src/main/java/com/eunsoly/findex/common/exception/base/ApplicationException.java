package com.eunsoly.findex.common.exception.base;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode; // 커스텀 에러 메세지
    private final Map<String, Object> context; // 예외 상세 내용
    private final Instant timestamp; // 예외 발생 시간

    /**
     * 오류 코드, 원본 예외를 포함하는 애플리케이션 예외를 생성합니다.
     *
     * @param errorCode errorCode 오류 코드
     * @param cause cause 원본 예외
     */
    protected ApplicationException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, errorCode.getDefaultMessage(), cause);
    }

    /**
     * 오류 코드, 메시지 및 원인 예외를 포함하는 애플리케이션 예외를 생성합니다.
     *
     * @param errorCode errorCode 오류 코드
     * @param message message 예외 메세지
     * @param cause cause 원본 예외
     */
    protected ApplicationException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.context = new HashMap<>();
        this.timestamp = Instant.now();
    }

    /**
     * 예외에 상세 정보를 추가합니다.
     *
     * @param key 상세 정보의 키
     * @param value 상세 정보의 값
     * @return 상세 정보가 추가된 현재 예외 객체
     */
    public ApplicationException addContext(String key, Object value) {
        this.context.put(key, value);
        return this;
    }
}
