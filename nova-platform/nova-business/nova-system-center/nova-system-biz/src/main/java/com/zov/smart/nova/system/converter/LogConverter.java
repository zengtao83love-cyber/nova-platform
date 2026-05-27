package com.zov.smart.nova.system.converter;

import com.zov.smart.nova.data.access.system.entity.SysLoginLogDO;
import com.zov.smart.nova.infra.audit.entity.AuditOperationLogDO;
import com.zov.smart.nova.system.api.vo.log.AuditLogVO;
import com.zov.smart.nova.system.api.vo.log.LoginLogVO;
import org.springframework.stereotype.Component;

/** Log converter. It deliberately never exposes response body in audit query VOs. */
@Component
public class LogConverter {

    public LoginLogVO toLoginLogVO(SysLoginLogDO source) {
        LoginLogVO vo = new LoginLogVO();
        vo.setId(source.getId());
        vo.setUserId(source.getUserId());
        vo.setUsername(source.getUsername());
        vo.setLoginIp(source.getLoginIp());
        vo.setUserAgent(source.getUserAgent());
        vo.setLoginResult(source.getLoginResult() == null ? null : source.getLoginResult().getCode());
        vo.setFailureReason(source.getFailureReason() == null ? null : source.getFailureReason().getCode());
        vo.setLoginAt(source.getLoginAt());
        return vo;
    }

    public AuditLogVO toAuditLogVO(AuditOperationLogDO source) {
        AuditLogVO vo = new AuditLogVO();
        vo.setId(source.getId());
        vo.setTraceId(source.getTraceId());
        vo.setOperationName(source.getOperationName());
        vo.setOperationType(source.getOperationType() == null ? null : source.getOperationType().getCode());
        vo.setRequestMethod(source.getRequestMethod());
        vo.setRequestUri(source.getRequestUri());
        vo.setRequestParams(source.getRequestParams());
        vo.setSuccessFlag(source.getSuccessFlag());
        vo.setErrorMessage(source.getErrorMessage());
        vo.setOperatorId(source.getOperatorId());
        vo.setOperatorName(source.getOperatorName());
        vo.setClientIp(source.getClientIp());
        vo.setUserAgent(source.getUserAgent());
        vo.setCostTimeMs(source.getCostTimeMs());
        vo.setCreatedAt(source.getCreatedAt());
        return vo;
    }
}
