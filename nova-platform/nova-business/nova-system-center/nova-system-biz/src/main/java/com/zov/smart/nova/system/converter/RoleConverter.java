package com.zov.smart.nova.system.converter;

import com.zov.smart.nova.data.access.system.entity.SysRoleDO;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.system.api.command.role.CreateRoleCommand;
import com.zov.smart.nova.system.api.command.role.UpdateRoleCommand;
import com.zov.smart.nova.system.api.dto.role.RoleDTO;
import com.zov.smart.nova.system.api.vo.role.RoleVO;
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
 * Role mapping contract. MapStruct generates the Spring bean implementation.
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleteFlag", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", expression = "java(toRoleStatus(command.getStatus()))")
    SysRoleDO toCreateDO(CreateRoleCommand command);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roleCode", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleteFlag", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "status", expression = "java(toRoleStatus(command.getStatus()))")
    void updateDO(UpdateRoleCommand command, @MappingTarget SysRoleDO target);

    @Mapping(target = "status", expression = "java(toCode(source.getStatus()))")
    @Mapping(target = "menuIds", expression = "java(copyLongList(menuIds))")
    RoleDTO toDTO(SysRoleDO source, List<Long> menuIds);

    @Mapping(target = "status", expression = "java(toCode(source.getStatus()))")
    @Mapping(target = "menuIds", expression = "java(copyLongList(menuIds))")
    RoleVO toVO(SysRoleDO source, List<Long> menuIds);

    @Mapping(target = "status", expression = "java(toCode(source.getStatus()))")
    @Mapping(target = "menuIds", expression = "java(new java.util.ArrayList<Long>())")
    RoleDTO toDTO(SysRoleDO source);

    default List<RoleDTO> toDTOList(List<SysRoleDO> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        List<RoleDTO> result = new ArrayList<RoleDTO>(source.size());
        for (SysRoleDO item : source) {
            result.add(toDTO(item));
        }
        return result;
    }

    default String toCode(RoleStatusEnum status) {
        return status == null ? null : status.getCode();
    }

    default RoleStatusEnum toRoleStatus(String status) {
        return StatusValidators.roleStatusOrDefault(status);
    }

    default List<Long> copyLongList(List<Long> values) {
        return values == null ? new ArrayList<Long>() : new ArrayList<Long>(values);
    }
}
