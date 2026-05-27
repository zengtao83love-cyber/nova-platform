package com.zov.smart.nova.infra.security.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class CurrentUserResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private String username;
    private String realName;
    private String nickname;
    private String mobile;
    private String email;
    private String avatar;
    private Boolean superAdminFlag = Boolean.FALSE;
    private Set<String> roles = new LinkedHashSet<String>();
    private Set<String> permissions = new LinkedHashSet<String>();

    public static CurrentUserResponse from(TokenSessionDO session) {
        CurrentUserResponse response = new CurrentUserResponse();
        response.setUserId(session.getUserId()); response.setUsername(session.getUsername());
        response.setRealName(session.getRealName()); response.setNickname(session.getNickname());
        response.setMobile(session.getMobile()); response.setEmail(session.getEmail()); response.setAvatar(session.getAvatar());
        response.setSuperAdminFlag(session.getSuperAdminFlag()); response.setRoles(session.getRoles()); response.setPermissions(session.getPermissions());
        return response;
    }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
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
    public Boolean getSuperAdminFlag() { return superAdminFlag; }
    public void setSuperAdminFlag(Boolean superAdminFlag) { this.superAdminFlag = Boolean.TRUE.equals(superAdminFlag); }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles == null ? new LinkedHashSet<String>() : new LinkedHashSet<String>(roles); }
    public Set<String> getPermissions() { return permissions; }
    public void setPermissions(Set<String> permissions) { this.permissions = permissions == null ? new LinkedHashSet<String>() : new LinkedHashSet<String>(permissions); }
}
