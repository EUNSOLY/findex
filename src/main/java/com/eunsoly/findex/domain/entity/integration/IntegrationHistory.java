package com.eunsoly.findex.domain.entity.integration;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntegrationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 기본 EAGER라서 LAZY로 변경
    @JoinColumn(name = "index_information_id", nullable = false) // FK 매핑할 컬럼명 지정
    private IndexInformation indexInformation;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private JobType jobType;

    @Column(nullable = true)
    private LocalDate targetDate;

    @Column(nullable = false)
    private String worker;

    @Column(nullable = false)
    private LocalDateTime jobTime;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ResultType result;

    public static IntegrationHistory createSuccessByIndexInfo(IndexInformation indexInformation, LocalDate targetDate, String worker,
            LocalDateTime jobTime) {
        IntegrationHistory integrationHistory = new IntegrationHistory();
        integrationHistory.indexInformation = indexInformation;
        integrationHistory.jobType = JobType.INDEX_INFO;
        integrationHistory.targetDate = targetDate;
        integrationHistory.worker = worker;
        integrationHistory.jobTime = jobTime;
        integrationHistory.result = ResultType.SUCCESS;

        return integrationHistory;
    }

    public static IntegrationHistory createFailedByIndexInfo(IndexInformation indexInformation, LocalDate targetDate, String worker,
            LocalDateTime jobTime) {
        IntegrationHistory integrationHistory = new IntegrationHistory();
        integrationHistory.indexInformation = indexInformation;
        integrationHistory.jobType = JobType.INDEX_INFO;
        integrationHistory.targetDate = targetDate;
        integrationHistory.worker = worker;
        integrationHistory.jobTime = jobTime;
        integrationHistory.result = ResultType.FAILED;

        return integrationHistory;
    }

    public static IntegrationHistory createSuccessByIndexData(IndexInformation indexInformation, LocalDate targetDate, String worker,
            LocalDateTime jobTime) {
        IntegrationHistory integrationHistory = new IntegrationHistory();
        integrationHistory.indexInformation = indexInformation;
        integrationHistory.jobType = JobType.INDEX_DATA;
        integrationHistory.targetDate = targetDate;
        integrationHistory.worker = worker;
        integrationHistory.jobTime = jobTime;
        integrationHistory.result = ResultType.SUCCESS;

        return integrationHistory;
    }

    public static IntegrationHistory createFailedByIndexData(IndexInformation indexInformation, LocalDate targetDate, String worker,
            LocalDateTime jobTime) {
        IntegrationHistory integrationHistory = new IntegrationHistory();
        integrationHistory.indexInformation = indexInformation;
        integrationHistory.jobType = JobType.INDEX_DATA;
        integrationHistory.targetDate = targetDate;
        integrationHistory.worker = worker;
        integrationHistory.jobTime = jobTime;
        integrationHistory.result = ResultType.FAILED;

        return integrationHistory;
    }
}
