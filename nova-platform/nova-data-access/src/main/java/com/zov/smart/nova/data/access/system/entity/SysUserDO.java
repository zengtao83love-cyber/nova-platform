package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;

import java.time.LocalDateTime;

/**
 * 系统用户持久化对象，对应表 sys_user。
 *
 * <p>password 只允许在认证/重置密码等持久层场景使用，后续 DTO/VO 转换必须禁止返回。</p>
 */
@TableName(value = "sys_user", autoResultMap = true)
public class SysUserDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("real_name")
    private String realName;

    @TableField("nickname")
    private String nickname;

    @TableField("mobile")
    private String mobile;

    @TableField("email")
    private String email;

    @TableField("avatar")
    private String avatar;

    @TableField(value = "status", typeHandler = EnumCodeTypeHandler.class)
    private UserStatusEnum status;

    @TableField("super_admin_flag")
    private Integer superAdminFlag;

    @TableField("login_lock_flag")
    private Integer loginLockFlag;

    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public UserStatusEnum getStatus() { return status; }
    public void setStatus(UserStatusEnum status) { this.status = status; }
    public Integer getSuperAdminFlag() { return superAdminFlag; }
    public void setSuperAdminFlag(Integer superAdminFlag) { this.superAdminFlag = superAdminFlag; }
    public Integer getLoginLockFlag() { return loginLockFlag; }
    public void setLoginLockFlag(Integer loginLockFlag) { this.loginLockFlag = loginLockFlag; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(LocalDateTime lastLoginAt) { this.lastLoginAt = lastLoginAt; }
}
