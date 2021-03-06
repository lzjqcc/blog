package com.lzj.domain;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Page implements Serializable{
    private static final long serialVersionUID = 8964663773098554965L;
    @NotNull
    private Integer currentPage;
    @NotNull
    private Integer pageSize;
    private Integer startIndex;
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public Page setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Page setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
        return this;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Page setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Page setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public String toString() {
        return "Page{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", startIndex=" + startIndex +
                ", count=" + count +
                '}';
    }
}
