package com.zov.smart.nova.system.converter;

import com.zov.smart.nova.data.access.system.entity.SysUserDO;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import com.zov.smart.nova.system.api.command.user.CreateUserCommand;
import com.zov.smart.nova.system.api.command.user.UpdateUserCommand;
import com.zov.smart.nova.system.api.dto.user.UserDTO;
import com.zov.smart.nova.system.api.vo.user.UserDetailVO;
import com.zov.smart.nova.system.api.vo.user.UserPageVO;
import com.zov.smart.nova.system.support.StatusValidators;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User mapping contract. MapStruct generates the Spring bean implementation.
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleteFlag", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "loginLockFlag", constant = "0")
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "superAdminFlag", constant = "0")
    @Mapping(target = "status", expression = "java(toUserStatus(command.getStatus()))")
    SysUserDO toCreateDO(CreateUserCommand command);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleteFlag", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "superAdminFlag", ignore = true)
    @Mapping(target = "loginLockFlag", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true)
    @Mapping(target = "status", expression = "java(toUserStatus(command.getStatus()))")
    void updateDO(UpdateUserCommand command, @MappingTarget SysUserDO target);

    @Mapping(target = "status", expression = "java(toCode(source.getStatus()))")
    @Mapping(target = "superAdminFlag", expression = "java(toBoolean(source.getSuperAdminFlag()))")
    UserDTO toDTO(SysUserDO source);

    @Mapping(target = "status", expression = "java(toCode(source.getStatus()))")
    @Mapping(target = "superAdminFlag", expression = "java(toBoolean(source.getSuperAdminFlag()))")
    UserPageVO toPageVO(SysUserDO source);

    @Mapping(target = "status", expression = "java(toCode(source.getStatus()))")
    @Mapping(target = "superAdminFlag", expression = "java(toBoolean(source.getSuperAdminFlag()))")
    @Mapping(target = "roleIds", expression = "java(copyLongList(roleIds))")
    UserDetailVO toDetailVO(SysUserDO source, List<Long> roleIds);

    default List<UserDTO> toDTOList(List<SysUserDO> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        List<UserDTO> result = new ArrayList<UserDTO>(source.size());
        for (SysUserDO item : source) {
            result.add(toDTO(item));
        }
        return result;
    }

    default String toCode(UserStatusEnum status) {
        return status == null ? null : status.getCode();
    }

    default Boolean toBoolean(Integer flag) {
        return Integer.valueOf(1).equals(flag);
    }

    default UserStatusEnum toUserStatus(String status) {
        return StatusValidators.userStatusOrDefault(status);
    }

    default List<Long> copyLongList(List<Long> values) {
        return values == null ? new ArrayList<Long>() : new ArrayList<Long>(values);
    }
}
