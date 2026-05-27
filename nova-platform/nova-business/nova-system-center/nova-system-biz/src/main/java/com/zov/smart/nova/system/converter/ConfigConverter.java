package com.zov.smart.nova.system.converter;

import com.zov.smart.nova.data.access.system.config.enums.ConfigTypeEnum;
import com.zov.smart.nova.data.access.system.entity.SysConfigDO;
import com.zov.smart.nova.system.api.command.config.CreateConfigCommand;
import com.zov.smart.nova.system.api.command.config.UpdateConfigCommand;
import com.zov.smart.nova.system.api.dto.config.ConfigDTO;
import com.zov.smart.nova.system.api.vo.config.ConfigVO;
import com.zov.smart.nova.system.support.StatusValidators;
import org.springframework.stereotype.Component;

/** System config converter. */
@Component
public class ConfigConverter {

    public SysConfigDO toCreateDO(CreateConfigCommand command) {
        SysConfigDO entity = new SysConfigDO();
        entity.setConfigKey(command.getConfigKey());
        entity.setConfigValue(command.getConfigValue());
        entity.setConfigName(command.getConfigName());
        entity.setConfigType(StatusValidators.configTypeOrDefault(command.getConfigType()));
        entity.setRemark(command.getRemark());
        return entity;
    }

    public void updateDO(UpdateConfigCommand command, SysConfigDO target) {
        target.setConfigValue(command.getConfigValue());
        target.setConfigName(command.getConfigName());
        if (command.getConfigType() != null && !command.getConfigType().trim().isEmpty()) {
            target.setConfigType(StatusValidators.requiredConfigType(command.getConfigType()));
        }
        target.setRemark(command.getRemark());
    }

    public ConfigDTO toDTO(SysConfigDO source) {
        if (source == null) {
            return null;
        }
        ConfigDTO dto = new ConfigDTO();
        dto.setId(source.getId());
        dto.setConfigKey(source.getConfigKey());
        dto.setConfigValue(source.getConfigValue());
        dto.setConfigName(source.getConfigName());
        dto.setConfigType(toCode(source.getConfigType()));
        dto.setRemark(source.getRemark());
        dto.setCreatedAt(source.getCreatedAt());
        dto.setUpdatedAt(source.getUpdatedAt());
        return dto;
    }

    public ConfigVO toVO(SysConfigDO source) {
        ConfigVO vo = new ConfigVO();
        vo.setId(source.getId());
        vo.setConfigKey(source.getConfigKey());
        vo.setConfigValue(source.getConfigValue());
        vo.setConfigName(source.getConfigName());
        vo.setConfigType(toCode(source.getConfigType()));
        vo.setRemark(source.getRemark());
        vo.setCreatedAt(source.getCreatedAt());
        vo.setUpdatedAt(source.getUpdatedAt());
        return vo;
    }

    public String toCode(ConfigTypeEnum type) {
        return type == null ? null : type.getCode();
    }
}
