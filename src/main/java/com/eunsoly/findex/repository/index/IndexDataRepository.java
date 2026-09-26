package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IndexDataRepository extends JpaRepository<IndexData, Long>, IndexDataRepositorySupport {
    Optional<IndexData> findByIndexInformationIdAndBaseDate(Long id, LocalDate baseDate);

    List<IndexData> findByIndexInformationId(Long indexInformationId);

    List<IndexData> findByIndexInformationIdAndBaseDateBetween(Long indexInformationId, LocalDate startDate, LocalDate endDate);

    List<IndexData> findByIndexInformationIdAndBaseDateBetweenOrderByBaseDateDesc(Long indexInformationId, LocalDate baseDateAfter,
            LocalDate baseDateBefore);
}
