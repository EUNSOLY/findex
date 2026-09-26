package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.common.exception.base.ErrorCode;
import com.eunsoly.findex.common.exception.index.IndexException;
import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.domain.entity.index.PeriodType;
import com.eunsoly.findex.domain.entity.index.SourceType;
import com.eunsoly.findex.repository.index.IndexDataRepository;
import com.eunsoly.findex.repository.index.IndexDataSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexDataServiceImpl implements IndexDataService {
    private final IndexDataRepository indexDataRepository;

    @Override
    public IndexData upsert(IndexData entity) {
        return indexDataRepository.findByIndexInformationIdAndBaseDate(entity.getIndexInformation().getId(), entity.getBaseDate()).map(existing -> {
            if (existing.getSourceType().equals(SourceType.USER)) {
                throw new IndexException(ErrorCode.DUPLICATE_INDEX_DATA, null);
            }
            existing.updateByIntegration(entity.getMarketPrice(), entity.getClosingPrice(), entity.getHighPrice(), entity.getLowPrice(),
                    entity.getVersus(), entity.getFluctuationRate(), entity.getTradingQuantity(), entity.getTradingPrice(),
                    entity.getMarketTotalAmount());
            return existing;
        }).orElseGet(() -> indexDataRepository.save(entity));
    }

    @Override
    public IndexDataCursorResult searchIndexData(IndexDataSearchCondition condition) {

        List<IndexData> indexData = indexDataRepository.searchIndexData(condition);
        Long totalElements = indexDataRepository.count(condition);

        Long nextIdAfter = null;
        String nextCursor = null;
        boolean hashNext = indexData.size() > condition.size();

        List<IndexData> indexDataResult = indexData.subList(0, Math.min(indexData.size(), condition.size()));

        if (!indexData.isEmpty()) {
            IndexData lastEntity = indexDataResult.getLast();
            nextIdAfter = lastEntity.getId();
            nextCursor = this.getLastSortValue(condition.sortField(), lastEntity);
        }

        CursorPaginationResult cursorPaginationResult = CursorPaginationResult.of(nextCursor, nextIdAfter, condition.size(), totalElements, hashNext);

        return new IndexDataCursorResult(indexDataResult, cursorPaginationResult);
    }

    @Override
    public void deleteById(Long id) {
        IndexData indexData = this.findById(id);
        indexDataRepository.delete(indexData);
    }

    @Override
    public IndexData findById(Long id) {
        return indexDataRepository.findById(id).orElseThrow(() -> new IndexException(ErrorCode.NOT_FOUnd_INDEX_DATA, null));
    }

    @Override
    public IndexChartDataResult getChartData(Long indexInformationId, String periodType) {
        PeriodType type = PeriodType.of(periodType);
        LocalDate endDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate startDate = getFromDate(endDate, type);

        List<IndexData> indexData =
                indexDataRepository.findByIndexInformationIdAndBaseDateBetweenOrderByBaseDateDesc(indexInformationId, startDate, endDate);
        List<ChartData> base = indexData.stream().map(data -> ChartData.of(data.getBaseDate().toString(), data.getClosingPrice())).toList();
        List<ChartData> ma5 = this.calculateMovingAverage(indexData, 5);
        List<ChartData> ma20 = this.calculateMovingAverage(indexData, 20);

        return IndexChartDataResult.of(base, ma5, ma20);
    }

    private List<ChartData> calculateMovingAverage(List<IndexData> sorted, Integer windowSize) {
        if (sorted.size() < windowSize) {
            return List.of();
        }

        List<ChartData> result = new ArrayList<>();
        for (int i = sorted.size() - 1; i > windowSize; i--) {
            List<IndexData> window = sorted.subList(i - windowSize + 1, i);
            String currentDate = sorted.get(i).getBaseDate().toString();
            BigDecimal value = window.stream().map(IndexData::getClosingPrice) // 각 IndexData → closingPrice만 추출
                    .reduce(BigDecimal.ZERO, BigDecimal::add) // 다 더함 (0부터 시작해서 누적 합)
                    .divide(new BigDecimal(windowSize), 2, RoundingMode.HALF_UP); // windowSize로 나눔, 소수 2자리, 반올림
            result.add(ChartData.of(currentDate, value));
        }
        return result;
    }


    private LocalDate getFromDate(LocalDate today, PeriodType type) {
        return switch (type) {
            case MONTHLY -> today.minusMonths(1);
            case QUARTERLY -> today.minusMonths(3);
            case YEARLY -> today.minusYears(1);
            default -> today;
        };
    }

    private String getLastSortValue(String sortField, IndexData indexData) {
        return switch (sortField) {
            case "closingPrice" -> String.valueOf(indexData.getClosingPrice());
            case "highPrice" -> String.valueOf(indexData.getHighPrice());
            case "lowPrice" -> String.valueOf(indexData.getLowPrice());
            case "versus" -> String.valueOf(indexData.getVersus());
            case "marketPrice" -> String.valueOf(indexData.getMarketPrice());
            case "fluctuationRate" -> String.valueOf(indexData.getFluctuationRate());
            case "tradingQuantity" -> String.valueOf(indexData.getTradingQuantity());
            default -> indexData.getBaseDate().toString();
        };
    }

}


