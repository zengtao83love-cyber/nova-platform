package com.zov.smart.nova.system.service;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import com.zov.smart.nova.system.api.query.user.UserPageQuery;

import java.util.Collection;
import java.util.List;

/** Atomic persistence operations for sys_user and sys_user_role. */
public interface UserService {
    SysUserDO findById(Long userId);
    SysUserDO getByIdRequired(Long userId);
    SysUserDO getByUsername(String username);
    List<SysUserDO> listByIds(Collection<Long> userIds);
    PageResult<SysUserDO> pageUsers(UserPageQuery query);
    boolean existsUser(Long userId);
    Long createUser(SysUserDO user);
    void updateUser(SysUserDO user);
    void logicDeleteUser(Long userId);
    void updateStatus(Long userId, UserStatusEnum status);
    void updatePassword(Long userId, String encodedPassword);
    void updateLoginLockFlag(Long userId, Integer lockFlag);
    List<Long> listRoleIds(Long userId);
    void replaceUserRoles(Long userId, Collection<Long> roleIds);
    long countEnabledSuperAdmins();
    List<Long> listUserIdsByRoleId(Long roleId);
}
