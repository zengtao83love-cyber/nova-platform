package com.zov.smart.nova.common.model;

import java.io.Serializable;

/**
 * Unified paging query parameter.
 *
 * <p>DTO matrix constraints: {@code pageNo >= 1}, {@code pageSize} defaults to 10
 * and is limited to {@code 1..100}. Setters normalize invalid values so callers do
 * not propagate illegal paging values to persistence infrastructure.</p>
 */
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 100;

    private Integer pageNo = DEFAULT_PAGE_NO;
    private Integer pageSize = DEFAULT_PAGE_SIZE;

    public PageParam() {
    }

    public PageParam(Integer pageNo, Integer pageSize) {
        setPageNo(pageNo);
        setPageSize(pageSize);
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo == null || pageNo < DEFAULT_PAGE_NO) {
            this.pageNo = DEFAULT_PAGE_NO;
            return;
        }
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = DEFAULT_PAGE_SIZE;
            return;
        }
        if (pageSize < 1) {
            this.pageSize = DEFAULT_PAGE_SIZE;
            return;
        }
        if (pageSize > MAX_PAGE_SIZE) {
            this.pageSize = MAX_PAGE_SIZE;
            return;
        }
        this.pageSize = pageSize;
    }

    public long offset() {
        return (long) (pageNo - 1) * pageSize;
    }
}
