package com.zov.smart.nova.common.context;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 登录用户会话模型。
 *
 * <p>该对象只承载已经完成认证后的用户快照，不负责解析 Token、访问 Redis、查询数据库或校验权限。
 * 后续 security 模块可以将 Redis 中的完整会话转换为 {@link LoginUserContext.CurrentUser} 写入线程上下文。</p>
 */
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId;
    private String username;
    private String realName;
    private Boolean superAdminFlag;
    private Set<String> roleCodes = new LinkedHashSet<>();
    private Set<String> permissionCodes = new LinkedHashSet<>();

    public LoginUser() {
    }

    public LoginUser(Long userId, String username, String realName, Boolean superAdminFlag) {
        this.userId = userId;
        this.username = username;
        this.realName = realName;
        this.superAdminFlag = superAdminFlag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getSuperAdminFlag() {
        return superAdminFlag;
    }

    public void setSuperAdminFlag(Boolean superAdminFlag) {
        this.superAdminFlag = superAdminFlag;
    }

    public boolean isSuperAdmin() {
        return Boolean.TRUE.equals(superAdminFlag);
    }

    public Set<String> getRoleCodes() {
        return Collections.unmodifiableSet(roleCodes);
    }

    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = copyToLinkedHashSet(roleCodes);
    }

    public Set<String> getPermissionCodes() {
        return Collections.unmodifiableSet(permissionCodes);
    }

    public void setPermissionCodes(Set<String> permissionCodes) {
        this.permissionCodes = copyToLinkedHashSet(permissionCodes);
    }

    public boolean hasPermission(String permissionCode) {
        return permissionCode != null && permissionCodes.contains(permissionCode);
    }

    public LoginUserContext.CurrentUser toCurrentUser() {
        LoginUserContext.CurrentUser currentUser = new LoginUserContext.CurrentUser();
        currentUser.setUserId(userId);
        currentUser.setUsername(username);
        currentUser.setRealName(realName);
        currentUser.setSuperAdminFlag(superAdminFlag);
        currentUser.setRoleCodes(roleCodes);
        currentUser.setPermissionCodes(permissionCodes);
        return currentUser;
    }

    private static Set<String> copyToLinkedHashSet(Set<String> source) {
        if (source == null || source.isEmpty()) {
            return new LinkedHashSet<>();
        }
        return new LinkedHashSet<>(source);
    }
}
