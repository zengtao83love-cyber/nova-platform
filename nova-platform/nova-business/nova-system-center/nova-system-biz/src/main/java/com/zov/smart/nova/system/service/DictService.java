package com.zov.smart.nova.system.service;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.data.access.system.entity.SysDictItemDO;
import com.zov.smart.nova.data.access.system.entity.SysDictTypeDO;
import com.zov.smart.nova.system.api.query.dict.DictItemPageQuery;
import com.zov.smart.nova.system.api.query.dict.DictTypePageQuery;

import java.util.List;

/** Atomic dictionary persistence service. */
public interface DictService {
    SysDictTypeDO getTypeByIdRequired(Long id);
    SysDictItemDO getItemByIdRequired(Long id);
    SysDictTypeDO findTypeByCode(String dictCode);
    PageResult<SysDictTypeDO> pageTypes(DictTypePageQuery query);
    PageResult<SysDictItemDO> pageItems(DictItemPageQuery query);
    List<SysDictItemDO> listItems(String dictCode);
    boolean existsItemValue(String dictCode, String itemValue, Long excludeId);
    Long createType(SysDictTypeDO type);
    void updateType(SysDictTypeDO type);
    void deleteType(Long id);
    Long createItem(SysDictItemDO item);
    void updateItem(SysDictItemDO item);
    void deleteItem(Long id);
}
