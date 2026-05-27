package com.zov.smart.nova.system.converter;

import com.zov.smart.nova.data.access.system.dict.enums.DictStatusEnum;
import com.zov.smart.nova.data.access.system.entity.SysDictItemDO;
import com.zov.smart.nova.data.access.system.entity.SysDictTypeDO;
import com.zov.smart.nova.system.api.command.dict.CreateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.CreateDictTypeCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictTypeCommand;
import com.zov.smart.nova.system.api.dto.dict.DictItemDTO;
import com.zov.smart.nova.system.api.dto.dict.DictTypeDTO;
import com.zov.smart.nova.system.api.vo.dict.DictItemVO;
import com.zov.smart.nova.system.api.vo.dict.DictTypeVO;
import com.zov.smart.nova.system.api.vo.dict.LabelValue;
import com.zov.smart.nova.system.support.StatusValidators;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Dictionary converter for type and item models. */
@Component
public class DictConverter {

    public SysDictTypeDO toCreateTypeDO(CreateDictTypeCommand command) {
        SysDictTypeDO entity = new SysDictTypeDO();
        entity.setDictCode(command.getDictCode());
        entity.setDictName(command.getDictName());
        entity.setStatus(StatusValidators.dictStatusOrDefault(command.getStatus()));
        entity.setRemark(command.getRemark());
        return entity;
    }

    public void updateTypeDO(UpdateDictTypeCommand command, SysDictTypeDO target) {
        target.setDictName(command.getDictName());
        if (command.getStatus() != null && !command.getStatus().trim().isEmpty()) {
            target.setStatus(StatusValidators.requiredDictStatus(command.getStatus()));
        }
        target.setRemark(command.getRemark());
    }

    public SysDictItemDO toCreateItemDO(CreateDictItemCommand command) {
        SysDictItemDO entity = new SysDictItemDO();
        entity.setDictCode(command.getDictCode());
        entity.setItemLabel(command.getItemLabel());
        entity.setItemValue(command.getItemValue());
        entity.setSortOrder(command.getSortOrder() == null ? 0 : command.getSortOrder());
        entity.setStatus(StatusValidators.dictStatusOrDefault(command.getStatus()));
        entity.setRemark(command.getRemark());
        return entity;
    }

    public void updateItemDO(UpdateDictItemCommand command, SysDictItemDO target) {
        target.setItemLabel(command.getItemLabel());
        target.setItemValue(command.getItemValue());
        target.setSortOrder(command.getSortOrder() == null ? 0 : command.getSortOrder());
        if (command.getStatus() != null && !command.getStatus().trim().isEmpty()) {
            target.setStatus(StatusValidators.requiredDictStatus(command.getStatus()));
        }
        target.setRemark(command.getRemark());
    }

    public DictTypeDTO toTypeDTO(SysDictTypeDO source) {
        if (source == null) {
            return null;
        }
        DictTypeDTO dto = new DictTypeDTO();
        dto.setId(source.getId());
        dto.setDictCode(source.getDictCode());
        dto.setDictName(source.getDictName());
        dto.setStatus(toCode(source.getStatus()));
        dto.setRemark(source.getRemark());
        dto.setCreatedAt(source.getCreatedAt());
        dto.setUpdatedAt(source.getUpdatedAt());
        return dto;
    }

    public DictTypeVO toTypeVO(SysDictTypeDO source) {
        DictTypeVO vo = new DictTypeVO();
        vo.setId(source.getId());
        vo.setDictCode(source.getDictCode());
        vo.setDictName(source.getDictName());
        vo.setStatus(toCode(source.getStatus()));
        vo.setRemark(source.getRemark());
        vo.setCreatedAt(source.getCreatedAt());
        vo.setUpdatedAt(source.getUpdatedAt());
        return vo;
    }

    public DictItemDTO toItemDTO(SysDictItemDO source) {
        if (source == null) {
            return null;
        }
        DictItemDTO dto = new DictItemDTO();
        dto.setId(source.getId());
        dto.setDictCode(source.getDictCode());
        dto.setItemLabel(source.getItemLabel());
        dto.setItemValue(source.getItemValue());
        dto.setSortOrder(source.getSortOrder());
        dto.setStatus(toCode(source.getStatus()));
        dto.setRemark(source.getRemark());
        dto.setCreatedAt(source.getCreatedAt());
        dto.setUpdatedAt(source.getUpdatedAt());
        return dto;
    }

    public DictItemVO toItemVO(SysDictItemDO source) {
        DictItemVO vo = new DictItemVO();
        vo.setId(source.getId());
        vo.setDictCode(source.getDictCode());
        vo.setItemLabel(source.getItemLabel());
        vo.setItemValue(source.getItemValue());
        vo.setSortOrder(source.getSortOrder());
        vo.setStatus(toCode(source.getStatus()));
        vo.setRemark(source.getRemark());
        vo.setCreatedAt(source.getCreatedAt());
        vo.setUpdatedAt(source.getUpdatedAt());
        return vo;
    }

    public LabelValue toLabelValue(SysDictItemDO source) {
        LabelValue vo = new LabelValue();
        vo.setLabel(source.getItemLabel());
        vo.setValue(source.getItemValue());
        vo.setDisabled(DictStatusEnum.DISABLED.equals(source.getStatus()));
        return vo;
    }

    public List<DictItemDTO> toItemDTOList(List<SysDictItemDO> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        List<DictItemDTO> result = new ArrayList<DictItemDTO>(source.size());
        for (SysDictItemDO item : source) {
            result.add(toItemDTO(item));
        }
        return result;
    }

    public String toCode(DictStatusEnum status) {
        return status == null ? null : status.getCode();
    }
}
