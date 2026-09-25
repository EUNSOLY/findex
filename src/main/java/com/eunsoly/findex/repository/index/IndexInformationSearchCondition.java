package com.eunsoly.findex.repository.index;


public record IndexInformationSearchCondition(
        // 필터
        String indexClassification, String indexName, Boolean favorite,
        // 커서 페이지네이션
        Long idAfter, String cursor, String sortField, String sortDirection, Integer size) {

    public static IndexInformationSearchCondition of(String indexClassification, String indexName, Boolean favorite, Long idAfter, String cursor,
            String sortField, String sortDirection, Integer size) {
        return new IndexInformationSearchCondition(indexClassification, indexName, favorite, idAfter, cursor, sortField, sortDirection, size);
    }
}
