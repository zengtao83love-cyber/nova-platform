package com.zov.smart.nova.system.api.query.user;

import com.zov.smart.nova.common.model.PageParam;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/** User management paging query contract. */
public class UserPageQuery extends PageParam {
    private static final long serialVersionUID = 1L;

    @Size(max = 64, message = "用户名长度不能超过64")
    private String username;
    @Size(max = 64, message = "真实姓名长度不能超过64")
    private String realName;
    @Size(max = 32, message = "手机号长度不能超过32")
    private String mobile;
    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "用户状态只能是ENABLED或DISABLED")
    private String status;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRealName() { return realName; }
    public void setRealName(String realName) { this.realName = realName; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
