package com.zov.smart.nova.infra.security.service;

import com.zov.smart.nova.data.access.system.entity.SysLoginLogDO;
import com.zov.smart.nova.data.access.system.log.enums.LoginFailureReasonEnum;
import com.zov.smart.nova.data.access.system.log.enums.LoginResultEnum;
import com.zov.smart.nova.data.access.system.mapper.SysLoginLogMapper;

import java.time.LocalDateTime;

/** Writes standardized login logs to sys_login_log. Failures never block login response generation. */
public class SecurityLoginLogService {
    private final SysLoginLogMapper sysLoginLogMapper;

    public SecurityLoginLogService(SysLoginLogMapper sysLoginLogMapper) { this.sysLoginLogMapper = sysLoginLogMapper; }

    public void recordSuccess(Long userId, String username, String ip, String userAgent) {
        save(userId, username, ip, userAgent, LoginResultEnum.SUCCESS, null);
    }

    public void recordFail(Long userId, String username, String ip, String userAgent, LoginFailureReasonEnum reason) {
        save(userId, username, ip, userAgent, LoginResultEnum.FAIL, reason);
    }

    private void save(Long userId, String username, String ip, String userAgent, LoginResultEnum result, LoginFailureReasonEnum reason) {
        try {
            SysLoginLogDO log = new SysLoginLogDO();
            log.setUserId(userId);
            log.setUsername(username);
            log.setLoginIp(ip);
            log.setUserAgent(userAgent);
            log.setLoginResult(result);
            log.setFailureReason(reason);
            log.setLoginAt(LocalDateTime.now());
            log.setCreatedAt(LocalDateTime.now());
            sysLoginLogMapper.insert(log);
        } catch (Exception ignored) {
            // Login log failure must not affect authentication result.
        }
    }
}
