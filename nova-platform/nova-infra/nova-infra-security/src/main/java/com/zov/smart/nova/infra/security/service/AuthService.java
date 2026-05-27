package com.zov.smart.nova.infra.security.service;

import com.zov.smart.nova.common.context.LoginUserContext;
import com.zov.smart.nova.common.core.error.AuthErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.util.IpUtils;
import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import com.zov.smart.nova.data.access.system.log.enums.LoginFailureReasonEnum;
import com.zov.smart.nova.data.access.system.mapper.SysUserMapper;
import com.zov.smart.nova.infra.security.brute.LoginBruteForceGuard;
import com.zov.smart.nova.infra.security.model.*;
import com.zov.smart.nova.infra.security.properties.NovaSecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/** Application service for AuthController. */
public class AuthService {
    private final SecurityUserService securityUserService;
    private final TokenService tokenService;
    private final LoginBruteForceGuard bruteForceGuard;
    private final SecurityLoginLogService loginLogService;
    private final SysUserMapper sysUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final NovaSecurityProperties properties;

    public AuthService(SecurityUserService securityUserService,
                       TokenService tokenService,
                       LoginBruteForceGuard bruteForceGuard,
                       SecurityLoginLogService loginLogService,
                       SysUserMapper sysUserMapper,
                       PasswordEncoder passwordEncoder,
                       NovaSecurityProperties properties) {
        this.securityUserService = securityUserService;
        this.tokenService = tokenService;
        this.bruteForceGuard = bruteForceGuard;
        this.loginLogService = loginLogService;
        this.sysUserMapper = sysUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.properties = properties;
    }

    public LoginResponse login(LoginRequest request, HttpServletRequest servletRequest) {
        String username = request.getUsername() == null ? null : request.getUsername().trim();
        String ip = IpUtils.getIpAddr(servletRequest);
        String ua = servletRequest == null ? null : servletRequest.getHeader("User-Agent");
        bruteForceGuard.checkNotLocked(username);
        SysUserDO user = securityUserService.loadByUsername(username);
        if (user == null) {
            bruteForceGuard.onLoginFail(username);
            loginLogService.recordFail(null, username, ip, ua, LoginFailureReasonEnum.AUTH_USER_NOT_FOUND);
            throw new BusinessException(AuthErrorCode.AUTH_USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            bruteForceGuard.onLoginFail(username);
            loginLogService.recordFail(user.getId(), username, ip, ua, LoginFailureReasonEnum.AUTH_PASSWORD_ERROR);
            throw new BusinessException(AuthErrorCode.AUTH_PASSWORD_ERROR);
        }
        if (!securityUserService.isEnabled(user)) {
            loginLogService.recordFail(user.getId(), username, ip, ua, LoginFailureReasonEnum.AUTH_USER_DISABLED);
            throw new BusinessException(AuthErrorCode.AUTH_USER_DISABLED);
        }
        TokenSessionDO session = securityUserService.buildSession(user, ip, ua);
        TokenPair pair = tokenService.createTokenPair(session);
        user.setLastLoginAt(LocalDateTime.now());
        sysUserMapper.updateById(user);
        bruteForceGuard.onLoginSuccess(username);
        loginLogService.recordSuccess(user.getId(), username, ip, ua);
        return new LoginResponse(pair.getAccessToken(), pair.getRefreshToken(), pair.getExpiresIn());
    }

    public void logout(String authorizationHeader) {
        String token = extractBearer(authorizationHeader);
        if (StringUtils.hasText(token)) { tokenService.revokeAccessToken(token); }
        LoginUserContext.clear();
    }

    public RefreshTokenResponse refresh(RefreshTokenRequest request) { return tokenService.refreshAccessToken(request.getRefreshToken()); }

    public CurrentUserResponse me() {
        LoginUserContext.CurrentUser current = requireCurrentUser();
        TokenSessionDO session = new TokenSessionDO();
        session.setUserId(current.getUserId()); session.setUsername(current.getUsername()); session.setRealName(current.getRealName());
        session.setSuperAdminFlag(current.getSuperAdminFlag()); session.setRoles(current.getRoleCodes()); session.setPermissions(current.getPermissionCodes());
        SysUserDO dbUser = sysUserMapper.selectById(current.getUserId());
        if (dbUser != null) {
            session.setNickname(dbUser.getNickname()); session.setMobile(dbUser.getMobile()); session.setEmail(dbUser.getEmail()); session.setAvatar(dbUser.getAvatar());
            session.setRealName(dbUser.getRealName());
        }
        return CurrentUserResponse.from(session);
    }

    public void updateProfile(UpdateProfileRequest request) {
        LoginUserContext.CurrentUser current = requireCurrentUser();
        SysUserDO user = sysUserMapper.selectById(current.getUserId());
        if (user == null) { throw new BusinessException(AuthErrorCode.AUTH_USER_NOT_FOUND); }
        user.setRealName(request.getRealName()); user.setNickname(request.getNickname()); user.setEmail(request.getEmail());
        user.setMobile(request.getMobile()); user.setAvatar(request.getAvatar());
        sysUserMapper.updateById(user);
    }

    public void changePassword(ChangePasswordRequest request) {
        LoginUserContext.CurrentUser current = requireCurrentUser();
        SysUserDO user = sysUserMapper.selectById(current.getUserId());
        if (user == null) { throw new BusinessException(AuthErrorCode.AUTH_USER_NOT_FOUND); }
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) { throw new BusinessException(AuthErrorCode.AUTH_OLD_PASSWORD_ERROR); }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) { throw new BusinessException(AuthErrorCode.AUTH_PASSWORD_NOT_MATCH); }
        if (!request.getNewPassword().matches(properties.getPasswordStrengthRegex())) { throw new BusinessException(AuthErrorCode.AUTH_PASSWORD_STRENGTH_INVALID); }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        sysUserMapper.updateById(user);
        tokenService.revokeAllUserTokens(user.getId());
    }

    public List<CurrentMenuVO> currentMenus() { return securityUserService.loadCurrentMenuTree(requireCurrentUser().getUserId()); }
    public PermissionCodeResponse currentPermissions() { return new PermissionCodeResponse(requireCurrentUser().getPermissionCodes()); }

    private LoginUserContext.CurrentUser requireCurrentUser() {
        LoginUserContext.CurrentUser currentUser = LoginUserContext.get();
        if (currentUser == null) { throw new BusinessException(AuthErrorCode.AUTH_UNAUTHORIZED); }
        return currentUser;
    }

    private String extractBearer(String header) {
        if (!StringUtils.hasText(header)) { return null; }
        String prefix = properties.normalizeTokenPrefix();
        if (header.startsWith(prefix + " ")) { return header.substring(prefix.length() + 1); }
        return header;
    }
}
