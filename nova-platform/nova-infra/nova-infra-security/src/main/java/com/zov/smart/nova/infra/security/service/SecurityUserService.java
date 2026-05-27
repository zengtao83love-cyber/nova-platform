package com.zov.smart.nova.infra.security.service;

import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import com.zov.smart.nova.data.access.system.entity.SysRoleDO;
import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import com.zov.smart.nova.data.access.system.mapper.SysMenuMapper;
import com.zov.smart.nova.data.access.system.mapper.SysRoleMapper;
import com.zov.smart.nova.data.access.system.mapper.SysUserMapper;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import com.zov.smart.nova.infra.security.model.CurrentMenuVO;
import com.zov.smart.nova.infra.security.model.TokenSessionDO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Loads security user snapshots from data-access. */
public class SecurityUserService {
    private final SysUserMapper sysUserMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysMenuMapper sysMenuMapper;

    public SecurityUserService(SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper, SysMenuMapper sysMenuMapper) {
        this.sysUserMapper = sysUserMapper;
        this.sysRoleMapper = sysRoleMapper;
        this.sysMenuMapper = sysMenuMapper;
    }

    public SysUserDO loadByUsername(String username) {
        if (!StringUtils.hasText(username)) { return null; }
        return sysUserMapper.selectByUsername(username.trim());
    }

    public TokenSessionDO buildSession(SysUserDO user, String clientIp, String userAgent) {
        TokenSessionDO session = new TokenSessionDO();
        session.setUserId(user.getId());
        session.setUsername(user.getUsername());
        session.setRealName(user.getRealName());
        session.setNickname(user.getNickname());
        session.setMobile(user.getMobile());
        session.setEmail(user.getEmail());
        session.setAvatar(user.getAvatar());
        session.setSuperAdminFlag(isSuperAdmin(user));
        session.setRoles(loadRoleCodes(user.getId()));
        session.setPermissions(loadPermissionCodes(user.getId()));
        session.setClientIp(clientIp);
        session.setUserAgent(userAgent);
        return session;
    }

    public boolean isEnabled(SysUserDO user) {
        return user != null && user.getStatus() == UserStatusEnum.ENABLED && !Integer.valueOf(1).equals(user.getLoginLockFlag());
    }

    public boolean isSuperAdmin(SysUserDO user) {
        return user != null && Integer.valueOf(1).equals(user.getSuperAdminFlag());
    }

    public Set<String> loadRoleCodes(Long userId) {
        List<SysRoleDO> roles = sysRoleMapper.selectEnabledRolesByUserId(userId);
        Set<String> result = new LinkedHashSet<String>();
        if (roles != null) {
            for (SysRoleDO role : roles) {
                if (role != null && StringUtils.hasText(role.getRoleCode())) {
                    result.add(role.getRoleCode());
                }
            }
        }
        return result;
    }

    public Set<String> loadPermissionCodes(Long userId) {
        List<String> permissions = sysMenuMapper.selectPermissionCodesByUserId(userId);
        Set<String> result = new LinkedHashSet<String>();
        if (permissions != null) {
            for (String permission : permissions) {
                if (StringUtils.hasText(permission)) { result.add(permission.trim()); }
            }
        }
        return result;
    }

    public List<CurrentMenuVO> loadCurrentMenuTree(Long userId) {
        List<SysMenuDO> menus = sysMenuMapper.selectMenusByUserId(userId);
        return toTree(menus);
    }

    private List<CurrentMenuVO> toTree(List<SysMenuDO> menus) {
        if (menus == null || menus.isEmpty()) { return Collections.emptyList(); }
        Collections.sort(menus, new Comparator<SysMenuDO>() {
            @Override public int compare(SysMenuDO a, SysMenuDO b) {
                int ao = a.getSortOrder() == null ? 0 : a.getSortOrder();
                int bo = b.getSortOrder() == null ? 0 : b.getSortOrder();
                return ao == bo ? Long.compare(a.getId() == null ? 0L : a.getId(), b.getId() == null ? 0L : b.getId()) : Integer.compare(ao, bo);
            }
        });
        Map<Long, CurrentMenuVO> idMap = new LinkedHashMap<Long, CurrentMenuVO>();
        List<CurrentMenuVO> roots = new ArrayList<CurrentMenuVO>();
        for (SysMenuDO menu : menus) {
            if (menu == null || menu.getMenuType() == MenuTypeEnum.BUTTON) { continue; }
            idMap.put(menu.getId(), CurrentMenuVO.from(menu));
        }
        for (CurrentMenuVO vo : idMap.values()) {
            Long parentId = vo.getParentId() == null ? 0L : vo.getParentId();
            CurrentMenuVO parent = idMap.get(parentId);
            if (parent == null || parentId == 0L) { roots.add(vo); }
            else { parent.getChildren().add(vo); }
        }
        return roots;
    }
}
