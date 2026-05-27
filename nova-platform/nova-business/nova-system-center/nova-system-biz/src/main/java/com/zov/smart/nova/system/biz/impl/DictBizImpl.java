package com.zov.smart.nova.system.biz.impl;

import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysDictItemDO;
import com.zov.smart.nova.data.access.system.entity.SysDictTypeDO;
import com.zov.smart.nova.system.api.command.dict.CreateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.CreateDictTypeCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictTypeCommand;
import com.zov.smart.nova.system.api.dto.dict.DictItemDTO;
import com.zov.smart.nova.system.api.dto.dict.DictTypeDTO;
import com.zov.smart.nova.system.api.query.dict.DictItemPageQuery;
import com.zov.smart.nova.system.api.query.dict.DictTypePageQuery;
import com.zov.smart.nova.system.api.vo.dict.DictItemVO;
import com.zov.smart.nova.system.api.vo.dict.DictTypeVO;
import com.zov.smart.nova.system.api.vo.dict.LabelValue;
import com.zov.smart.nova.system.biz.DictBiz;
import com.zov.smart.nova.system.converter.DictConverter;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.DictService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Dictionary business implementation. */
@Service
public class DictBizImpl implements DictBiz {

    private final DictService dictService;
    private final DictConverter dictConverter;

    public DictBizImpl(DictService dictService, DictConverter dictConverter) {
        this.dictService = dictService;
        this.dictConverter = dictConverter;
    }

    @Override
    public PageResult<DictTypeVO> pageDictTypes(DictTypePageQuery query) {
        PageResult<SysDictTypeDO> page = dictService.pageTypes(query);
        List<DictTypeVO> records = new ArrayList<DictTypeVO>();
        for (SysDictTypeDO item : page.getRecords()) {
            records.add(dictConverter.toTypeVO(item));
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    public PageResult<DictItemVO> pageDictItems(DictItemPageQuery query) {
        PageResult<SysDictItemDO> page = dictService.pageItems(query);
        List<DictItemVO> records = new ArrayList<DictItemVO>();
        for (SysDictItemDO item : page.getRecords()) {
            records.add(dictConverter.toItemVO(item));
        }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }

    @Override
    public List<DictItemDTO> listDictItems(String dictCode) {
        return dictConverter.toItemDTOList(dictService.listItems(dictCode));
    }

    @Override
    public List<LabelValue> listOptions(String dictCode) {
        List<SysDictItemDO> items = dictService.listItems(dictCode);
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        List<LabelValue> result = new ArrayList<LabelValue>(items.size());
        for (SysDictItemDO item : items) {
            result.add(dictConverter.toLabelValue(item));
        }
        return result;
    }

    @Override
    public String getDictLabel(String dictCode, String value) {
        for (SysDictItemDO item : dictService.listItems(dictCode)) {
            if (value != null && value.equals(item.getItemValue())) {
                return item.getItemLabel();
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictType(CreateDictTypeCommand command) {
        if (dictService.findTypeByCode(command.getDictCode()) != null) {
            throw new BusinessException(SystemErrorCode.DICT_TYPE_CODE_EXISTS);
        }
        return dictService.createType(dictConverter.toCreateTypeDO(command));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictType(UpdateDictTypeCommand command) {
        SysDictTypeDO type = dictService.getTypeByIdRequired(command.getId());
        dictConverter.updateTypeDO(command, type);
        dictService.updateType(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(Long dictTypeId) {
        dictService.getTypeByIdRequired(dictTypeId);
        dictService.deleteType(dictTypeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDictItem(CreateDictItemCommand command) {
        if (dictService.findTypeByCode(command.getDictCode()) == null) {
            throw new BusinessException(SystemErrorCode.DICT_TYPE_NOT_FOUND);
        }
        if (dictService.existsItemValue(command.getDictCode(), command.getItemValue(), null)) {
            throw new BusinessException(SystemErrorCode.DICT_ITEM_VALUE_EXISTS);
        }
        return dictService.createItem(dictConverter.toCreateItemDO(command));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDictItem(UpdateDictItemCommand command) {
        SysDictItemDO item = dictService.getItemByIdRequired(command.getId());
        if (dictService.existsItemValue(item.getDictCode(), command.getItemValue(), item.getId())) {
            throw new BusinessException(SystemErrorCode.DICT_ITEM_VALUE_EXISTS);
        }
        dictConverter.updateItemDO(command, item);
        dictService.updateItem(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDictItem(Long dictItemId) {
        dictService.getItemByIdRequired(dictItemId);
        dictService.deleteItem(dictItemId);
    }

    @Override
    public DictTypeDTO getTypeByCode(String dictCode) {
        SysDictTypeDO type = dictService.findTypeByCode(dictCode);
        if (type == null) {
            throw new BusinessException(SystemErrorCode.DICT_TYPE_NOT_FOUND);
        }
        return dictConverter.toTypeDTO(type);
    }
}
