package com.zov.smart.nova.system.api.facade;

import com.zov.smart.nova.system.api.vo.enums.EntityEnumVO;
import java.util.List;

/** Runtime enum query contract. */
public interface EnumFacade {
    List<EntityEnumVO> listAllEntityEnums();
    EntityEnumVO getEntityEnums(String entityName);
}
