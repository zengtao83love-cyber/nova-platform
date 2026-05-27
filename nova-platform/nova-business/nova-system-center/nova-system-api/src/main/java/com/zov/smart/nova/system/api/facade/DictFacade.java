package com.zov.smart.nova.system.api.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.dict.CreateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.CreateDictTypeCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictTypeCommand;
import com.zov.smart.nova.system.api.dto.dict.DictItemDTO;
import com.zov.smart.nova.system.api.dto.dict.DictTypeDTO;
import com.zov.smart.nova.system.api.query.dict.DictItemPageQuery;
import com.zov.smart.nova.system.api.query.dict.DictTypePageQuery;
import java.util.List;

/** Dictionary contract exposed by system center. */
public interface DictFacade {
    PageResult<DictTypeDTO> pageDictTypes(DictTypePageQuery query);
    PageResult<DictItemDTO> pageDictItems(DictItemPageQuery query);
    List<DictItemDTO> listDictItems(String dictCode);
    String getDictLabel(String dictCode, String value);
    Long createDictType(CreateDictTypeCommand command);
    void updateDictType(UpdateDictTypeCommand command);
    void deleteDictType(Long dictTypeId);
    Long createDictItem(CreateDictItemCommand command);
    void updateDictItem(UpdateDictItemCommand command);
    void deleteDictItem(Long dictItemId);
}
