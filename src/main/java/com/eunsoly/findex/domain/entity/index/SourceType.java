package com.eunsoly.findex.domain.entity.index;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum SourceType {
    OPEN_API("OPEN_API"),
    USER("USER");

    String value;

    public static SourceType of(String type) {

        for (SourceType sourceType : SourceType.values()) {
            if (sourceType.getValue().equals(type)) {
                return sourceType;
            }
        }
        // TODO : 커스텀 예외 생성하기
        throw new RuntimeException("");
    }
}
