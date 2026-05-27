package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.common.model.PageResult;
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

import java.util.List;

/** Dictionary business orchestration. */
public interface DictBiz {
    PageResult<DictTypeVO> pageDictTypes(DictTypePageQuery query);
    PageResult<DictItemVO> pageDictItems(DictItemPageQuery query);
    List<DictItemDTO> listDictItems(String dictCode);
    List<LabelValue> listOptions(String dictCode);
    String getDictLabel(String dictCode, String value);
    Long createDictType(CreateDictTypeCommand command);
    void updateDictType(UpdateDictTypeCommand command);
    void deleteDictType(Long dictTypeId);
    Long createDictItem(CreateDictItemCommand command);
    void updateDictItem(UpdateDictItemCommand command);
    void deleteDictItem(Long dictItemId);
    DictTypeDTO getTypeByCode(String dictCode);
}
