package com.zov.smart.nova.infra.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.infra.audit.model.AuditLogQueryCriteria;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper owned by nova-infra-audit. It must not be moved into nova-data-access.
 */
public interface AuditOperationLogMapper extends BaseMapper<AuditOperationLogDO> {

    AuditOperationLogDO selectOneById(@Param("id") Long id);

    Page<AuditOperationLogDO> selectPageByCriteria(Page<AuditOperationLogDO> page,
                                                   @Param("criteria") AuditLogQueryCriteria criteria);
}
