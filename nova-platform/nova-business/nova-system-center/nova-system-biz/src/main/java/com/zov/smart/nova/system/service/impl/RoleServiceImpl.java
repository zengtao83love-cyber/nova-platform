package com.zov.smart.nova.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysMenuDO;
import com.zov.smart.nova.data.access.system.entity.SysRoleDO;
import com.zov.smart.nova.data.access.system.entity.SysRoleMenuDO;
import com.zov.smart.nova.data.access.system.entity.SysUserRoleDO;
import com.zov.smart.nova.data.access.system.mapper.SysMenuMapper;
import com.zov.smart.nova.data.access.system.mapper.SysRoleMapper;
import com.zov.smart.nova.data.access.system.mapper.SysRoleMenuMapper;
import com.zov.smart.nova.data.access.system.mapper.SysUserRoleMapper;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.system.api.query.role.RolePageQuery;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Atomic role persistence implementation. */
@Service
public class RoleServiceImpl implements RoleService {

    private final SysRoleMapper roleMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysRoleMenuMapper roleMenuMapper;
    private final SysMenuMapper menuMapper;

    public RoleServiceImpl(SysRoleMapper roleMapper,
                           SysUserRoleMapper userRoleMapper,
                           SysRoleMenuMapper roleMenuMapper,
                           SysMenuMapper menuMapper) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public SysRoleDO findById(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return roleMapper.selectById(roleId);
    }

    @Override
    public SysRoleDO getByIdRequired(Long roleId) {
        SysRoleDO role = findById(roleId);
        if (role == null) {
            throw new BusinessException(SystemErrorCode.ROLE_NOT_FOUND);
        }
        return role;
    }

    @Override
    public List<SysRoleDO> listByIds(Collection<Long> roleIds) {
        Set<Long> ids = sanitizeIds(roleIds);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMapper.selectBatchIds(ids);
    }

    @Override
    public List<SysRoleDO> listRolesByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return roleMapper.selectRolesByUserId(userId);
    }

    @Override
    public List<SysRoleDO> listEnabledRolesByUserId(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return roleMapper.selectEnabledRolesByUserId(userId);
    }

    @Override
    public PageResult<SysRoleDO> pageRoles(RolePageQuery query) {
        RolePageQuery safe = query == null ? new RolePageQuery() : query;
        IPage<SysRoleDO> page = new Page<SysRoleDO>(safe.getPageNo(), safe.getPageSize());
        IPage<SysRoleDO> result = roleMapper.selectRolePage(page, safe.getRoleCode(), safe.getRoleName(), safe.getStatus());
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }

    @Override
    public boolean existsRole(Long roleId) {
        return findById(roleId) != null;
    }

    @Override
    public boolean existsAllRoleIds(Collection<Long> roleIds) {
        Set<Long> ids = sanitizeIds(roleIds);
        if (ids.isEmpty()) {
            return true;
        }
        return listByIds(ids).size() == ids.size();
    }

    @Override
    public Long createRole(SysRoleDO role) {
        roleMapper.insert(role);
        return role.getId();
    }

    @Override
    public void updateRole(SysRoleDO role) {
        roleMapper.updateById(role);
    }

    @Override
    public void logicDeleteRole(Long roleId) {
        roleMapper.deleteById(roleId);
    }

    @Override
    public void updateStatus(Long roleId, RoleStatusEnum status) {
        SysRoleDO entity = new SysRoleDO();
        entity.setId(roleId);
        entity.setStatus(status);
        roleMapper.updateById(entity);
    }

    @Override
    public boolean roleCodeExists(String roleCode, Long excludeRoleId) {
        if (roleCode == null || roleCode.trim().isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<SysRoleDO> wrapper = new LambdaQueryWrapper<SysRoleDO>()
                .eq(SysRoleDO::getRoleCode, roleCode.trim());
        if (excludeRoleId != null) {
            wrapper.ne(SysRoleDO::getId, excludeRoleId);
        }
        Long count = roleMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    public boolean isRoleInUse(Long roleId) {
        if (roleId == null) {
            return false;
        }
        LambdaQueryWrapper<SysUserRoleDO> wrapper = new LambdaQueryWrapper<SysUserRoleDO>()
                .eq(SysUserRoleDO::getRoleId, roleId);
        Long count = userRoleMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    public List<Long> listMenuIds(Long roleId) {
        if (roleId == null) {
            return Collections.emptyList();
        }
        return roleMenuMapper.selectMenuIdsByRoleId(roleId);
    }

    @Override
    public void replaceRoleMenus(Long roleId, Collection<Long> menuIds) {
        roleMenuMapper.deleteByRoleId(roleId);
        Set<Long> ids = sanitizeIds(menuIds);
        if (!ids.isEmpty()) {
            roleMenuMapper.batchInsert(roleId, ids);
        }
    }

    @Override
    public boolean existsAllMenuIds(Collection<Long> menuIds) {
        Set<Long> ids = sanitizeIds(menuIds);
        if (ids.isEmpty()) {
            return true;
        }
        List<SysMenuDO> menus = menuMapper.selectBatchIds(ids);
        return menus != null && menus.size() == ids.size();
    }

    @Override
    public List<Long> listUserIdsByRoleId(Long roleId) {
        if (roleId == null) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SysUserRoleDO> wrapper = new LambdaQueryWrapper<SysUserRoleDO>()
                .eq(SysUserRoleDO::getRoleId, roleId);
        List<SysUserRoleDO> relations = userRoleMapper.selectList(wrapper);
        List<Long> result = new ArrayList<Long>();
        if (relations != null) {
            for (SysUserRoleDO relation : relations) {
                if (relation.getUserId() != null) {
                    result.add(relation.getUserId());
                }
            }
        }
        return result;
    }

    private Set<Long> sanitizeIds(Collection<Long> ids) {
        Set<Long> result = new LinkedHashSet<Long>();
        if (ids == null) {
            return result;
        }
        for (Long id : ids) {
            if (id != null) {
                result.add(id);
            }
        }
        return result;
    }
}
