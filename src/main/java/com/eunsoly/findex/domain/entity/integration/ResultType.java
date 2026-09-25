package com.eunsoly.findex.domain.entity.integration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum ResultType {
    SUCCESS("SUCCESS"),
    FAILED("FAILED");

    String value;
}
