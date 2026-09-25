package com.eunsoly.findex.domain.entity.index;

import com.eunsoly.findex.domain.entity.SourceType;
import com.eunsoly.findex.domain.entity.integration.IntegrationConfig;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@ToString(exclude = "integrationConfig")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"index_classification", "index_name"}))
public class IndexInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String indexClassification; // 지수 분류명

    @Column(nullable = false)
    private String indexName; // 지수명

    @Column(nullable = false)
    private Integer employedItemsCount; // 채용종목수

    @Column(nullable = false)
    private LocalDate basePointInTime; // 기준시점

    @Column(nullable = false)
    private Float baseIndex; // 기준지수

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    @Column(nullable = false)
    private Boolean favorite; // 즐겨찾기

    @OneToOne(mappedBy = "indexInformation", cascade = CascadeType.ALL, orphanRemoval = true)
    private IntegrationConfig integrationConfig;

    public static IndexInformation createByUser(String indexClassification, String indexName, Integer employedItemsCount, LocalDate basePointInTime,
            Float baseIndex, Boolean favorite) {
        IndexInformation indexInformation = new IndexInformation();
        indexInformation.indexClassification = indexClassification;
        indexInformation.indexName = indexName;
        indexInformation.employedItemsCount = employedItemsCount;
        indexInformation.basePointInTime = basePointInTime;
        indexInformation.baseIndex = baseIndex;
        indexInformation.favorite = favorite;
        indexInformation.sourceType = SourceType.USER;

        IntegrationConfig.create(indexInformation);

        return indexInformation;
    }

    public static IndexInformation createByIntegration(String indexClassification, String indexName, Integer employedItemsCount,
            LocalDate basePointInTime, Float baseIndex) {
        IndexInformation indexInformation = new IndexInformation();
        indexInformation.indexClassification = indexClassification;
        indexInformation.indexName = indexName;
        indexInformation.employedItemsCount = employedItemsCount;
        indexInformation.basePointInTime = basePointInTime;
        indexInformation.baseIndex = baseIndex;
        indexInformation.favorite = false;
        indexInformation.sourceType = SourceType.OPEN_API;

        IntegrationConfig.create(indexInformation);

        return indexInformation;
    }

    public IndexInformation updateByUser(Integer employedItemsCount, LocalDate basePointInTime, Float baseIndex, Boolean favorite) {
        boolean isIndexInfoChanged = false;
        if (!this.employedItemsCount.equals(employedItemsCount)) {
            this.employedItemsCount = employedItemsCount;
            isIndexInfoChanged = true;
        }
        if (!this.basePointInTime.equals(basePointInTime)) {
            this.basePointInTime = basePointInTime;
            isIndexInfoChanged = true;
        }
        if (!this.baseIndex.equals(baseIndex)) {
            this.baseIndex = baseIndex;
            isIndexInfoChanged = true;
        }

        if (favorite != null) {
            this.favorite = favorite;
        }

        if (isIndexInfoChanged) {
            this.sourceType = SourceType.USER;
        }

        return this;
    }

    public IndexInformation updateByIntegration(Integer employedItemsCount, LocalDate basePointInTime, Float baseIndex) {
        this.employedItemsCount = employedItemsCount;
        this.basePointInTime = basePointInTime;
        this.baseIndex = baseIndex;
        this.sourceType = SourceType.OPEN_API;

        return this;
    }
}
