package com.zov.smart.nova.system.api.vo.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDetailVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String realName;

    private String nickname;

    private String mobile;

    private String email;

    private String status;

    private Boolean superAdminFlag;

    private LocalDateTime lastLoginAt;

    private String avatar;

    private List<Long> roleIds;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
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


            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }


            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }


            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }


            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }


            public Boolean getSuperAdminFlag() {
                return superAdminFlag;
            }

            public void setSuperAdminFlag(Boolean superAdminFlag) {
                this.superAdminFlag = superAdminFlag;
            }


            public LocalDateTime getLastLoginAt() {
                return lastLoginAt;
            }

            public void setLastLoginAt(LocalDateTime lastLoginAt) {
                this.lastLoginAt = lastLoginAt;
            }


            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }


            public List<Long> getRoleIds() {
                return roleIds;
            }

            public void setRoleIds(List<Long> roleIds) {
                this.roleIds = roleIds;
            }


            public LocalDateTime getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
            }


            public LocalDateTime getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
            }

    public UserDetailVO() { this.status = "ENABLED"; this.superAdminFlag = Boolean.FALSE; this.roleIds = new ArrayList<Long>(); }

}
