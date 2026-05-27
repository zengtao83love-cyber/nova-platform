package com.zov.smart.nova.infra.security.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/** Login request. Password must never be written to audit/log payloads in raw form. */
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 64)
    private String username;

    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
