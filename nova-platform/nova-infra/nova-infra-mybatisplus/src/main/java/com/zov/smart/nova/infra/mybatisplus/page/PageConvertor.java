package com.zov.smart.nova.infra.mybatisplus.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zov.smart.nova.common.model.PageResult;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Converts MyBatis-Plus native page objects to the platform {@link PageResult} contract.
 *
 * <p>Controller and API contracts must never expose MyBatis-Plus {@link IPage} directly.</p>
 */
public final class PageConvertor {

    private PageConvertor() {
    }

    public static <T, R> PageResult<R> convert(IPage<T> iPage, Function<T, R> mapper) {
        if (iPage == null) {
            return PageResult.empty();
        }
        Function<T, R> safeMapper = mapper == null ? identityMapper() : mapper;
        List<T> sourceRecords = iPage.getRecords() == null ? Collections.<T>emptyList() : iPage.getRecords();
        List<R> records = sourceRecords.stream().map(safeMapper).collect(Collectors.toList());
        return PageResult.of(iPage.getCurrent(), iPage.getSize(), iPage.getTotal(), records);
    }

    public static <T> PageResult<T> convert(IPage<T> iPage) {
        return convert(iPage, null);
    }

    @SuppressWarnings("unchecked")
    private static <T, R> Function<T, R> identityMapper() {
        return value -> (R) value;
    }
}
