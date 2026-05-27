package com.zov.smart.nova.system.facade;

import com.zov.smart.nova.system.api.facade.EnumFacade;
import com.zov.smart.nova.system.api.vo.enums.EntityEnumVO;
import com.zov.smart.nova.system.biz.EnumBiz;
import org.springframework.stereotype.Service;

import java.util.List;

/** Enum facade implementation. */
@Service
public class EnumFacadeImpl implements EnumFacade {
    private final EnumBiz enumBiz;
    public EnumFacadeImpl(EnumBiz enumBiz) { this.enumBiz = enumBiz; }
    @Override public List<EntityEnumVO> listAllEntityEnums() { return enumBiz.listAllEntityEnums(); }
    @Override public EntityEnumVO getEntityEnums(String entityName) { return enumBiz.getEntityEnums(entityName); }
}
