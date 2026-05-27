package com.zov.smart.nova.infra.security.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RefreshTokenRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotBlank
    private String refreshToken;
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
}
