package com.eunsoly.findex.repository.index;

import com.eunsoly.findex.domain.entity.index.IndexData;

public record IndexDataRankPair(IndexData current, IndexData before) {
}
