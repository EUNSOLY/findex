package com.eunsoly.findex.domain.service.index;

import com.eunsoly.findex.common.dto.CursorPaginationResult;
import com.eunsoly.findex.domain.entity.index.IndexData;
import com.eunsoly.findex.repository.index.IndexDataRepository;
import com.eunsoly.findex.repository.index.IndexDataSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexDataServiceImpl implements IndexDataService {
    private final IndexDataRepository indexDataRepository;

    @Override
    public IndexData upsert(IndexData entity) {
        return null;
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
