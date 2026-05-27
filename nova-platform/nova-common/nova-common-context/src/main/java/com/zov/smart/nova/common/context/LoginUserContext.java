package com.zov.smart.nova.common.context;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 当前登录用户线程上下文。
 *
 * <p>本类只封装 ThreadLocal，不解析 Token、不访问数据库、不访问 Redis、不做权限校验。
 * Web 请求结束时必须在 finally 中调用 {@link #clear()}，避免 Tomcat 线程复用导致用户串号。</p>
 */
public final class LoginUserContext {
    private static final ThreadLocal<CurrentUser> USER_HOLDER = new ThreadLocal<CurrentUser>();

    private LoginUserContext() {}

    public static void set(CurrentUser currentUser) {
        if (currentUser == null) { clear(); return; }
        USER_HOLDER.set(currentUser);
    }

    public static void set(LoginUser loginUser) {
        if (loginUser == null) { clear(); return; }
        set(loginUser.toCurrentUser());
    }

    public static CurrentUser get() { return USER_HOLDER.get(); }

    public static boolean hasLoginUser() { return USER_HOLDER.get() != null; }

    public static Long currentUserId() {
        CurrentUser currentUser = USER_HOLDER.get();
        return currentUser == null ? null : currentUser.getUserId();
    }

    /** 后台任务、初始化脚本或无登录态场景的审计兜底用户 ID。 */
    public static Long currentUserIdOrSystem() {
        Long userId = currentUserId();
        return userId == null ? 0L : userId;
    }

    /** 强力屏障：请求生命周期结束时必须执行 clear，防止线程复用导致越权串号。 */
    public static void clear() { USER_HOLDER.remove(); }

    /** 当前用户轻量模型，承载审计、自动填充、权限上下文需要的字段。 */
    public static class CurrentUser implements Serializable {
        private static final long serialVersionUID = 1L;

        private Long userId;
        private String username;
        private String realName;
        private Boolean superAdminFlag;
        private Set<String> roleCodes = new LinkedHashSet<String>();
        private Set<String> permissionCodes = new LinkedHashSet<String>();

        public CurrentUser() {}

        public CurrentUser(Long userId, String username, String realName, Boolean superAdminFlag) {
            this(userId, username, realName, superAdminFlag, null, null);
        }

        public CurrentUser(Long userId, String username, String realName, Boolean superAdminFlag,
                           Set<String> roleCodes, Set<String> permissionCodes) {
            this.userId = userId;
            this.username = username;
            this.realName = realName;
            this.superAdminFlag = Boolean.TRUE.equals(superAdminFlag);
            setRoleCodes(roleCodes);
            setPermissionCodes(permissionCodes);
        }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getRealName() { return realName; }
        public void setRealName(String realName) { this.realName = realName; }
        public Boolean getSuperAdminFlag() { return superAdminFlag; }
        public void setSuperAdminFlag(Boolean superAdminFlag) { this.superAdminFlag = Boolean.TRUE.equals(superAdminFlag); }
        public Set<String> getRoleCodes() { return roleCodes; }
        public void setRoleCodes(Set<String> roleCodes) { this.roleCodes = roleCodes == null ? new LinkedHashSet<String>() : new LinkedHashSet<String>(roleCodes); }
        public Set<String> getPermissionCodes() { return permissionCodes; }
        public void setPermissionCodes(Set<String> permissionCodes) { this.permissionCodes = permissionCodes == null ? new LinkedHashSet<String>() : new LinkedHashSet<String>(permissionCodes); }
        public boolean isSuperAdmin() { return Boolean.TRUE.equals(superAdminFlag); }
    }
}
