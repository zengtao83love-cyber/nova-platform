package com.zov.smart.nova.common.mybatis.handler;

import com.zov.smart.nova.common.mybatis.enums.DbEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现 DbEnum 的枚举字段转换处理器。
 */
public class EnumCodeTypeHandler<E extends Enum<?> & DbEnum> extends BaseTypeHandler<E> {
    private final Class<E> type;

    public EnumCodeTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toEnum(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toEnum(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toEnum(cs.getString(columnIndex));
    }

    private E toEnum(String code) {
        if (code == null || code.trim().isEmpty()) {
            return null;
        }
        E[] constants = type.getEnumConstants();
        for (E constant : constants) {
            if (constant.getCode().equals(code)) {
                return constant;
            }
        }
        return null;
    }
}
