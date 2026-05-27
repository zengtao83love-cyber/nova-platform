package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.system.api.command.dict.CreateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.CreateDictTypeCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictTypeCommand;
import com.zov.smart.nova.system.api.dto.dict.DictItemDTO;
import com.zov.smart.nova.system.api.dto.dict.DictTypeDTO;
import com.zov.smart.nova.system.api.facade.DictFacade;
import com.zov.smart.nova.system.api.query.dict.DictItemPageQuery;
import com.zov.smart.nova.system.api.query.dict.DictTypePageQuery;
import com.zov.smart.nova.system.api.vo.dict.DictItemVO;
import com.zov.smart.nova.system.api.vo.dict.DictTypeVO;
import com.zov.smart.nova.system.biz.DictBiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** Dictionary facade implementation. */
@Service
public class DictFacadeImpl implements DictFacade {
    private final DictBiz dictBiz;
    public DictFacadeImpl(DictBiz dictBiz) { this.dictBiz = dictBiz; }
    @Override public PageResult<DictTypeDTO> pageDictTypes(DictTypePageQuery query) {
        PageResult<DictTypeVO> page = dictBiz.pageDictTypes(query);
        List<DictTypeDTO> records = new ArrayList<DictTypeDTO>();
        for (DictTypeVO item : page.getRecords()) { records.add(toTypeDTO(item)); }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }
    @Override public PageResult<DictItemDTO> pageDictItems(DictItemPageQuery query) {
        PageResult<DictItemVO> page = dictBiz.pageDictItems(query);
        List<DictItemDTO> records = new ArrayList<DictItemDTO>();
        for (DictItemVO item : page.getRecords()) { records.add(toItemDTO(item)); }
        return PageResult.of(page.getPageNo(), page.getPageSize(), page.getTotal(), records);
    }
    @Override public List<DictItemDTO> listDictItems(String dictCode) { return dictBiz.listDictItems(dictCode); }
    @Override public String getDictLabel(String dictCode, String value) { return dictBiz.getDictLabel(dictCode, value); }
    @Override public Long createDictType(CreateDictTypeCommand command) { return dictBiz.createDictType(command); }
    @Override public void updateDictType(UpdateDictTypeCommand command) { dictBiz.updateDictType(command); }
    @Override public void deleteDictType(Long dictTypeId) { dictBiz.deleteDictType(dictTypeId); }
    @Override public Long createDictItem(CreateDictItemCommand command) { return dictBiz.createDictItem(command); }
    @Override public void updateDictItem(UpdateDictItemCommand command) { dictBiz.updateDictItem(command); }
    @Override public void deleteDictItem(Long dictItemId) { dictBiz.deleteDictItem(dictItemId); }
    private DictTypeDTO toTypeDTO(DictTypeVO item) { DictTypeDTO dto = new DictTypeDTO(); dto.setId(item.getId()); dto.setDictCode(item.getDictCode()); dto.setDictName(item.getDictName()); dto.setStatus(item.getStatus()); dto.setRemark(item.getRemark()); dto.setCreatedAt(item.getCreatedAt()); dto.setUpdatedAt(item.getUpdatedAt()); return dto; }
    private DictItemDTO toItemDTO(DictItemVO item) { DictItemDTO dto = new DictItemDTO(); dto.setId(item.getId()); dto.setDictCode(item.getDictCode()); dto.setItemLabel(item.getItemLabel()); dto.setItemValue(item.getItemValue()); dto.setSortOrder(item.getSortOrder()); dto.setStatus(item.getStatus()); dto.setRemark(item.getRemark()); dto.setCreatedAt(item.getCreatedAt()); dto.setUpdatedAt(item.getUpdatedAt()); return dto; }
}
