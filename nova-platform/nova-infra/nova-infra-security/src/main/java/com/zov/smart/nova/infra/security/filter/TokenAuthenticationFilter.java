package com.zov.smart.nova.infra.security.filter;

import com.zov.smart.nova.common.context.LoginUserContext;
import com.zov.smart.nova.infra.security.model.TokenSessionDO;
import com.zov.smart.nova.infra.security.properties.NovaSecurityProperties;
import com.zov.smart.nova.infra.security.service.TokenService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Parses Authorization header, loads Redis TokenSessionDO, and writes LoginUserContext. */
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final NovaSecurityProperties properties;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public TokenAuthenticationFilter(TokenService tokenService, NovaSecurityProperties properties) {
        this.tokenService = tokenService;
        this.properties = properties;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        for (String pattern : properties.getIgnoreUrls()) {
            if (antPathMatcher.match(pattern, path)) { return true; }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            if (StringUtils.hasText(token)) {
                TokenSessionDO session = tokenService.loadByAccessToken(token);
                LoginUserContext.CurrentUser currentUser = new LoginUserContext.CurrentUser(
                        session.getUserId(), session.getUsername(), session.getRealName(), session.getSuperAdminFlag(), session.getRoles(), session.getPermissions());
                LoginUserContext.set(currentUser);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        session.getUsername(), null, toAuthorities(session)));
            }
            filterChain.doFilter(request, response);
        } finally {
            LoginUserContext.clear();
            SecurityContextHolder.clearContext();
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(properties.getTokenHeader());
        if (!StringUtils.hasText(header)) { header = request.getHeader(HttpHeaders.AUTHORIZATION); }
        if (!StringUtils.hasText(header)) { return null; }
        String prefix = properties.normalizeTokenPrefix();
        if (header.startsWith(prefix + " ")) { return header.substring(prefix.length() + 1); }
        return header;
    }

    private List<SimpleGrantedAuthority> toAuthorities(TokenSessionDO session) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (String permission : session.getPermissions()) { authorities.add(new SimpleGrantedAuthority(permission)); }
        return authorities;
    }
}
