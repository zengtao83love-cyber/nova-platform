package com.zov.smart.nova.infra.security.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ChangePasswordRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    @Size(min = 8, max = 64)
    private String oldPassword;
    @NotBlank
    @Size(min = 8, max = 64)
    private String newPassword;
    @NotBlank
    @Size(min = 8, max = 64)
    private String confirmPassword;

    public String getOldPassword() { return oldPassword; }
    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
