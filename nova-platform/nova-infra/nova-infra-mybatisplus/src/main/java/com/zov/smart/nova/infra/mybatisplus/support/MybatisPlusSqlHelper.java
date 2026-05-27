package com.zov.smart.nova.infra.mybatisplus.support;

import java.util.Collection;

/**
 * Small SQL-related guards used by upper persistence orchestration code.
 *
 * <p>This helper intentionally contains no Mapper, DO, or SQL execution logic.</p>
 */
public final class MybatisPlusSqlHelper {

    private MybatisPlusSqlHelper() {
    }

    public static boolean hasIds(Collection<Long> ids) {
        return ids != null && !ids.isEmpty();
    }

    public static boolean isAffected(int rows) {
        return rows > 0;
    }
}
