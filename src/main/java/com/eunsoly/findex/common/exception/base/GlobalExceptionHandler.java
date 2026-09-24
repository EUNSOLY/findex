package com.eunsoly.findex.common.exception.base;

import com.eunsoly.findex.common.exception.index.IndexException;
import com.eunsoly.findex.common.exception.integration.IntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IndexException.class)
    public ResponseEntity<ErrorResponse> handle(IndexException exception) {
        log.makeLoggingEventBuilder(exception.getErrorCode().getLevel())
                .log("지수 도메인 예외 발생 : {}", exception.getMessage(), exception);

        // 컨텍스트 정보 로깅
        exception
                .getContext()
                .forEach((key, value) -> log.debug("지수 도메인 Context: {} = {}", key, value));
        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(ErrorResponse.fail(exception.getMessage(), exception.getContext()));
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponse> handle(IntegrationException exception) {
        log.makeLoggingEventBuilder(exception.getErrorCode().getLevel())
                .log("연동 작업 도메인 예외 발생 : {}", exception.getMessage(), exception);

        // 컨텍스트 정보 로깅
        exception
                .getContext()
                .forEach((key, value) -> log.debug("연동 작업 Context: {} = {}", key, value));

        return ResponseEntity.status(exception.getErrorCode().getHttpStatus())
                .body(ErrorResponse.fail(exception.getMessage(), exception.getContext()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        log.error("알 수 없는 예외 발생: {}", exception.getClass().getSimpleName(), exception);

        return ResponseEntity.status(500).body(ErrorResponse.fail(exception.getMessage(), null));
    }
}
