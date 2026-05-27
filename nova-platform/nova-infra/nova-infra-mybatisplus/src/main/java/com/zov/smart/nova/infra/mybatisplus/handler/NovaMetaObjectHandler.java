package com.zov.smart.nova.infra.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zov.smart.nova.common.context.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus audit field auto-fill handler.
 *
 * <p>Fill strategy is conservative: insert fills create/update audit fields, logical-delete
 * marker and version when they are empty; update only fills update audit fields. When no
 * login user exists, the handler falls back to system user id {@code 0L}.</p>
 */
public class NovaMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATED_BY = "createdBy";
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_BY = "updatedBy";
    private static final String UPDATED_AT = "updatedAt";
    private static final String DELETE_FLAG = "deleteFlag";
    private static final String VERSION = "version";

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Long userId = LoginUserContext.currentUserIdOrSystem();

        this.strictInsertFill(metaObject, CREATED_BY, Long.class, userId);
        this.strictInsertFill(metaObject, CREATED_AT, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, UPDATED_BY, Long.class, userId);
        this.strictInsertFill(metaObject, UPDATED_AT, LocalDateTime.class, now);
        this.strictInsertFill(metaObject, DELETE_FLAG, Integer.class, 0);
        this.strictInsertFill(metaObject, VERSION, Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        Long userId = LoginUserContext.currentUserIdOrSystem();

        this.strictUpdateFill(metaObject, UPDATED_BY, Long.class, userId);
        this.strictUpdateFill(metaObject, UPDATED_AT, LocalDateTime.class, now);
    }
}
