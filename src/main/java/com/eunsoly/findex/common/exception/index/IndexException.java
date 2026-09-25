package com.eunsoly.findex.common.exception.index;

import com.eunsoly.findex.common.exception.base.ApplicationException;
import com.eunsoly.findex.common.exception.base.ErrorCode;

import java.util.Map;

public class IndexException extends ApplicationException {

    /**
     * 기본 메시지로 예외를 생성합니다.
     *
     * @param errorCode 에러 코드 (HTTP 상태, 기본 메시지, 로그 레벨 포함)
     * @param cause 원인 예외 (외부 API 호출 실패 등)
     */
    public IndexException(ErrorCode errorCode, Throwable cause) {
        this(errorCode, errorCode.getDefaultMessage(), null, cause);
    }

    /**
     * 직접 지정한 메시지로 예외를 생성합니다.
     *
     * @param errorCode 에러 코드
     * @param message 에러 코드의 기본 메시지 대신 사용할 메시지
     * @param cause 원인 예외
     */
    public IndexException(ErrorCode errorCode, String message, Throwable cause) {
        this(errorCode, message, null, cause);
    }

    /**
     * 기본 메시지와 추가 정보로 예외를 생성합니다.
     *
     * @param errorCode 에러 코드
     * @param detail 디버깅용 추가 정보 (예: 요청 URL, 지수 ID). 로그와 에러 응답에 포함됨
     * @param cause 원인 예외
     */
    public IndexException(ErrorCode errorCode, Map<String, Object> detail, Throwable cause) {
        this(errorCode, errorCode.getDefaultMessage(), detail, cause);
    }

    /**
     * 직접 지정한 메시지와 추가 정보로 예외를 생성합니다.
     *
     * <p>
     * 다른 생성자들은 모두 이 생성자를 호출합니다.
     *
     * @param errorCode 에러 코드
     * @param message 에러 코드의 기본 메시지 대신 사용할 메시지
     * @param detail 디버깅용 추가 정보. {@code null}이면 무시됨
     * @param cause 원인 예외
     */
    public IndexException(ErrorCode errorCode, String message, Map<String, Object> detail, Throwable cause) {
        super(errorCode, message, cause);
        if (detail != null) {
            detail.forEach(this::addContext);
        }
    }
}
