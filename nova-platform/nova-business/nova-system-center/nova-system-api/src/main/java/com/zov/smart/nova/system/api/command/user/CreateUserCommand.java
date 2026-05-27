package com.zov.smart.nova.system.api.command.user;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class CreateUserCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 4, max = 32, message = "用户名长度必须在4-32之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名仅支持字母、数字及下划线")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 64, message = "密码长度必须在8-64之间")
    private String password;

    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 64, message = "真实姓名长度不能超过64")
    private String realName;

    @Size(max = 64, message = "昵称长度不能超过64")
    private String nickname;

    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;

    @Email(message = "邮箱格式不正确")
    @Size(max = 128, message = "邮箱长度不能超过128")
    private String email;

    @Size(max = 500, message = "头像地址长度不能超过500")
    private String avatar;

    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "用户状态只能是ENABLED或DISABLED")
    private String status;

    @Valid
    private List<Long> roleIds;




            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }


            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
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


            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }


            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }


            public List<Long> getRoleIds() {
                return roleIds;
            }

            public void setRoleIds(List<Long> roleIds) {
                this.roleIds = roleIds;
            }

    public CreateUserCommand() {
        this.status = "ENABLED";
        this.roleIds = new ArrayList<Long>();
    }

}
