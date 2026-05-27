package com.zov.smart.nova.infra.security.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.infra.security.model.*;
import com.zov.smart.nova.infra.security.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/** Auth APIs belong to nova-infra-security by specification. */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpServletRequest servletRequest) {
        return Result.success(authService.login(request, servletRequest));
    }

    @PostMapping("/logout")
    public Result<Void> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        authService.logout(authorization);
        return Result.success();
    }

    @PostMapping("/refresh")
    public Result<RefreshTokenResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return Result.success(authService.refresh(request));
    }

    @GetMapping("/me")
    public Result<CurrentUserResponse> me() { return Result.success(authService.me()); }

    @PutMapping("/me/profile")
    public Result<Void> updateProfile(@RequestBody @Valid UpdateProfileRequest request) {
        authService.updateProfile(request);
        return Result.success();
    }

    @PutMapping("/me/password")
    public Result<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        authService.changePassword(request);
        return Result.success();
    }

    @GetMapping("/menus")
    public Result<List<CurrentMenuVO>> menus() { return Result.success(authService.currentMenus()); }

    @GetMapping("/permissions")
    public Result<PermissionCodeResponse> permissions() { return Result.success(authService.currentPermissions()); }
}
