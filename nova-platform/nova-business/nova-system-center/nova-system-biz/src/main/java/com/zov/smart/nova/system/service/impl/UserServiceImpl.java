package com.zov.smart.nova.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import com.zov.smart.nova.data.access.system.entity.SysUserRoleDO;
import com.zov.smart.nova.data.access.system.mapper.SysUserMapper;
import com.zov.smart.nova.data.access.system.mapper.SysUserRoleMapper;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Atomic user persistence implementation. No write transaction is declared here;
 * transaction orchestration lives in UserBizImpl.
 */
@Service
public class UserServiceImpl implements UserService {

    private final SysUserMapper userMapper;
    private final SysUserRoleMapper userRoleMapper;

    public UserServiceImpl(SysUserMapper userMapper, SysUserRoleMapper userRoleMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public SysUserDO findById(Long userId) {
        if (userId == null) {
            return null;
        }
        return userMapper.selectById(userId);
    }

    @Override
    public SysUserDO getByIdRequired(Long userId) {
        SysUserDO user = findById(userId);
        if (user == null) {
            throw new BusinessException(SystemErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public SysUserDO getByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }
        return userMapper.selectByUsername(username.trim());
    }

    @Override
    public List<SysUserDO> listByIds(Collection<Long> userIds) {
        Set<Long> ids = sanitizeIds(userIds);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<SysUserDO> pageUsers(UserPageQuery query) {
        UserPageQuery safe = query == null ? new UserPageQuery() : query;
        IPage<SysUserDO> page = new Page<SysUserDO>(safe.getPageNo(), safe.getPageSize());
        IPage<SysUserDO> result = userMapper.selectUserPage(page, safe.getUsername(), safe.getRealName(), safe.getMobile(), safe.getStatus());
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }

    @Override
    public boolean existsUser(Long userId) {
        return findById(userId) != null;
    }

    @Override
    public Long createUser(SysUserDO user) {
        userMapper.insert(user);
        return user.getId();
    }

    @Override
    public void updateUser(SysUserDO user) {
        userMapper.updateById(user);
    }

    @Override
    public void logicDeleteUser(Long userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public void updateStatus(Long userId, UserStatusEnum status) {
        SysUserDO entity = new SysUserDO();
        entity.setId(userId);
        entity.setStatus(status);
        userMapper.updateById(entity);
    }

    @Override
    public void updatePassword(Long userId, String encodedPassword) {
        LambdaUpdateWrapper<SysUserDO> wrapper = new LambdaUpdateWrapper<SysUserDO>()
                .eq(SysUserDO::getId, userId)
                .set(SysUserDO::getPassword, encodedPassword);
        userMapper.update(null, wrapper);
    }

    @Override
    public void updateLoginLockFlag(Long userId, Integer lockFlag) {
        LambdaUpdateWrapper<SysUserDO> wrapper = new LambdaUpdateWrapper<SysUserDO>()
                .eq(SysUserDO::getId, userId)
                .set(SysUserDO::getLoginLockFlag, lockFlag == null ? 0 : lockFlag);
        userMapper.update(null, wrapper);
    }

    @Override
    public List<Long> listRoleIds(Long userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return userRoleMapper.selectRoleIdsByUserId(userId);
    }

    @Override
    public void replaceUserRoles(Long userId, Collection<Long> roleIds) {
        userRoleMapper.deleteByUserId(userId);
        Set<Long> ids = sanitizeIds(roleIds);
        if (!ids.isEmpty()) {
            userRoleMapper.batchInsert(userId, ids);
        }
    }

    @Override
    public long countEnabledSuperAdmins() {
        LambdaQueryWrapper<SysUserDO> wrapper = new LambdaQueryWrapper<SysUserDO>()
                .eq(SysUserDO::getStatus, UserStatusEnum.ENABLED)
                .eq(SysUserDO::getSuperAdminFlag, 1);
        Long count = userMapper.selectCount(wrapper);
        return count == null ? 0L : count;
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
