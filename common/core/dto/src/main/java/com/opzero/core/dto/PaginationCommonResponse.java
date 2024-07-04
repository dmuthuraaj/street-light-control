package com.opzero.core.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaginationCommonResponse<T> extends CommonResponse<T> {

    private Pagination page;


    @Data
    public static class Pagination {
        private long totalCount;
        private long totalPage;
        private long currentPage;
        private long previousPage;
        private long nextPage;
        private long itemPerPage;
    }
}
