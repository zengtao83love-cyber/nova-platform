package com.zov.smart.nova.system.biz;

import com.zov.smart.nova.system.api.vo.enums.EntityEnumVO;

import java.util.List;

/** Runtime enum query business contract. */
public interface EnumBiz {
    List<EntityEnumVO> listAllEntityEnums();
    EntityEnumVO getEntityEnums(String entityName);
}
