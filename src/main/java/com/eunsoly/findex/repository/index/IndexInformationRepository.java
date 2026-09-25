package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndexInformationRepository extends JpaRepository<IndexInformation, Long>, IndexInformationRepositorySupport {
    Optional<IndexInformation> findByIndexClassificationAndIndexName(String indexClassification, String indexName);
}
