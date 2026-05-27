package com.zov.smart.nova.infra.mybatisplus.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.model.PageParam;

/**
 * Creates MyBatis-Plus {@link Page} objects from the platform paging contract.
 */
public final class MybatisPageHelper {

    private MybatisPageHelper() {
    }

    public static <T> Page<T> toPage(PageParam pageParam) {
        PageParam safeParam = pageParam == null ? new PageParam() : pageParam;
        return new Page<T>(safeParam.getPageNo(), safeParam.getPageSize());
    }

    public static <T> Page<T> toPage(Integer pageNo, Integer pageSize) {
        return toPage(new PageParam(pageNo, pageSize));
    }
}
