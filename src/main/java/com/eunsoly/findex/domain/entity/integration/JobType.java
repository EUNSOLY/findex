package com.eunsoly.findex.domain.entity.integration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum JobType {
    INDEX_DATA("INDEX_DATA"),
    INDEX_INFO("INDEX_INFO");

    String value;

    public static JobType of(String type) {

        for (JobType jobType : JobType.values()) {
            if (jobType.getValue().equals(type)) {
                return jobType;
            }
        }
        throw new RuntimeException("");
    }
}
