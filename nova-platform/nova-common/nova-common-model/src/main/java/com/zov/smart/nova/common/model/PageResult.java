package com.zov.smart.nova.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unified paging response model.
 *
 * @param <T> record element type
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long pageNo;
    private long pageSize;
    private long total;
    private List<T> records;

    public PageResult() {
        this(PageParam.DEFAULT_PAGE_NO, PageParam.DEFAULT_PAGE_SIZE, 0L, Collections.<T>emptyList());
    }

    public PageResult(long pageNo, long pageSize, long total, List<T> records) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records == null ? Collections.<T>emptyList() : new ArrayList<T>(records);
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<T>();
    }

    public static <T> PageResult<T> of(long pageNo, long pageSize, long total, List<T> records) {
        return new PageResult<T>(pageNo, pageSize, total, records);
    }

    public long getPageNo() {
        return pageNo;
    }

    public void setPageNo(long pageNo) {
        this.pageNo = pageNo;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records == null ? Collections.<T>emptyList() : new ArrayList<T>(records);
    }
}
