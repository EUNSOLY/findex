package com.eunsoly.findex.domain.entity.integration;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IntegrationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_information_id", nullable = false)
    private IndexInformation indexInformation;

    public static IntegrationConfig create(IndexInformation indexInformation) {
        IntegrationConfig integrationConfig = new IntegrationConfig();
        integrationConfig.indexInformation = indexInformation;
        integrationConfig.enabled = false;

        return integrationConfig;
    }

    public IntegrationConfig update(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
