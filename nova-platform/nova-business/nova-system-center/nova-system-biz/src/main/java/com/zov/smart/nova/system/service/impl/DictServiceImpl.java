package com.zov.smart.nova.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysDictItemDO;
import com.zov.smart.nova.data.access.system.entity.SysDictTypeDO;
import com.zov.smart.nova.data.access.system.mapper.SysDictItemMapper;
import com.zov.smart.nova.data.access.system.mapper.SysDictTypeMapper;
import com.zov.smart.nova.system.api.query.dict.DictItemPageQuery;
import com.zov.smart.nova.system.api.query.dict.DictTypePageQuery;
import com.zov.smart.nova.system.error.SystemErrorCode;
import com.zov.smart.nova.system.service.DictService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/** Atomic dictionary persistence implementation. */
@Service
public class DictServiceImpl implements DictService {

    private final SysDictTypeMapper dictTypeMapper;
    private final SysDictItemMapper dictItemMapper;

    public DictServiceImpl(SysDictTypeMapper dictTypeMapper, SysDictItemMapper dictItemMapper) {
        this.dictTypeMapper = dictTypeMapper;
        this.dictItemMapper = dictItemMapper;
    }

    @Override
    public SysDictTypeDO getTypeByIdRequired(Long id) {
        SysDictTypeDO type = id == null ? null : dictTypeMapper.selectById(id);
        if (type == null) {
            throw new BusinessException(SystemErrorCode.DICT_TYPE_NOT_FOUND);
        }
        return type;
    }

    @Override
    public SysDictItemDO getItemByIdRequired(Long id) {
        SysDictItemDO item = id == null ? null : dictItemMapper.selectById(id);
        if (item == null) {
            throw new BusinessException(SystemErrorCode.DICT_ITEM_NOT_FOUND);
        }
        return item;
    }

    @Override
    public SysDictTypeDO findTypeByCode(String dictCode) {
        return dictCode == null || dictCode.trim().isEmpty() ? null : dictTypeMapper.selectByDictCode(dictCode.trim());
    }

    @Override
    public PageResult<SysDictTypeDO> pageTypes(DictTypePageQuery query) {
        DictTypePageQuery safe = query == null ? new DictTypePageQuery() : query;
        IPage<SysDictTypeDO> page = new Page<SysDictTypeDO>(safe.getPageNo(), safe.getPageSize());
        IPage<SysDictTypeDO> result = dictTypeMapper.selectDictTypePage(page, safe.getDictCode(), safe.getDictName(), safe.getStatus());
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }

    @Override
    public PageResult<SysDictItemDO> pageItems(DictItemPageQuery query) {
        DictItemPageQuery safe = query == null ? new DictItemPageQuery() : query;
        IPage<SysDictItemDO> page = new Page<SysDictItemDO>(safe.getPageNo(), safe.getPageSize());
        IPage<SysDictItemDO> result = dictItemMapper.selectDictItemPage(page, safe.getDictCode(), safe.getItemLabel(), safe.getStatus());
        return PageResult.of(result.getCurrent(), result.getSize(), result.getTotal(), result.getRecords());
    }

    @Override
    public List<SysDictItemDO> listItems(String dictCode) {
        if (dictCode == null || dictCode.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return dictItemMapper.selectItemsByDictCode(dictCode.trim());
    }

    @Override
    public boolean existsItemValue(String dictCode, String itemValue, Long excludeId) {
        if (dictCode == null || itemValue == null) {
            return false;
        }
        LambdaQueryWrapper<SysDictItemDO> wrapper = new LambdaQueryWrapper<SysDictItemDO>()
                .eq(SysDictItemDO::getDictCode, dictCode)
                .eq(SysDictItemDO::getItemValue, itemValue);
        if (excludeId != null) {
            wrapper.ne(SysDictItemDO::getId, excludeId);
        }
        Long count = dictItemMapper.selectCount(wrapper);
        return count != null && count > 0;
    }

    @Override
    public Long createType(SysDictTypeDO type) {
        dictTypeMapper.insert(type);
        return type.getId();
    }

    @Override
    public void updateType(SysDictTypeDO type) {
        dictTypeMapper.updateById(type);
    }

    @Override
    public void deleteType(Long id) {
        dictTypeMapper.deleteById(id);
    }

    @Override
    public Long createItem(SysDictItemDO item) {
        dictItemMapper.insert(item);
        return item.getId();
    }

    @Override
    public void updateItem(SysDictItemDO item) {
        dictItemMapper.updateById(item);
    }

    @Override
    public void deleteItem(Long id) {
        dictItemMapper.deleteById(id);
    }
}
