package com.zov.smart.nova.common.mybatis.enums;

/**
 * 数据库枚举统一契约。
 *
 * <p>业务枚举持久化 code 必须是 String，禁止使用 Integer 作为业务枚举持久化值。</p>
 */
public interface DbEnum {
    /**
     * 持久化到数据库的字符串编码。
     */
    String getCode();

    /**
     * 前台展示文本。
     */
    String getLabel();
}
