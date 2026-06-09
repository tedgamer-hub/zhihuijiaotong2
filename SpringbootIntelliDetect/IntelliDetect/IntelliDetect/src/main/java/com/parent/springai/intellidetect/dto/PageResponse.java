package com.parent.springai.intellidetect.dto;

import java.util.List;

public class PageResponse<T> {

    private Long total;
    private List<T> records;
    private Integer page;
    private Integer size;
    private Integer totalPages;

    // Constructors
    public PageResponse() {}

    public PageResponse(Long total, List<T> records, Integer page, Integer size, Integer totalPages) {
        this.total = total;
        this.records = records;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
    }

    // Getters and Setters
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
