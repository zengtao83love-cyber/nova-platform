package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.log.enums.LoginFailureReasonEnum;
import com.zov.smart.nova.data.access.system.log.enums.LoginResultEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志持久化对象，对应表 sys_login_log。
 *
 * <p>该表是追加型日志表，数据库结构只包含 id、登录字段和 created_at，
 * 不包含 updated_at、delete_flag、version 等完整 BaseDO 字段，因此不继承 BaseDO。</p>
 */
@TableName(value = "sys_login_log", autoResultMap = true)
public class SysLoginLogDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("username")
    private String username;

    @TableField("login_ip")
    private String loginIp;

    @TableField("user_agent")
    private String userAgent;

    @TableField(value = "login_result", typeHandler = EnumCodeTypeHandler.class)
    private LoginResultEnum loginResult;

    @TableField(value = "failure_reason", typeHandler = EnumCodeTypeHandler.class)
    private LoginFailureReasonEnum failureReason;

    @TableField("login_at")
    private LocalDateTime loginAt;

    @TableField("created_at")
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getLoginIp() { return loginIp; }
    public void setLoginIp(String loginIp) { this.loginIp = loginIp; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public LoginResultEnum getLoginResult() { return loginResult; }
    public void setLoginResult(LoginResultEnum loginResult) { this.loginResult = loginResult; }
    public LoginFailureReasonEnum getFailureReason() { return failureReason; }
    public void setFailureReason(LoginFailureReasonEnum failureReason) { this.failureReason = failureReason; }
    public LocalDateTime getLoginAt() { return loginAt; }
    public void setLoginAt(LocalDateTime loginAt) { this.loginAt = loginAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
