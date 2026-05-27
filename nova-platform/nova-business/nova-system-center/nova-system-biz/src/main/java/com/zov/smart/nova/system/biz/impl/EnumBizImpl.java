package com.zov.smart.nova.system.biz.impl;

import com.zov.smart.nova.common.core.error.CommonErrorCode;
import com.zov.smart.nova.common.core.exception.BusinessException;
import com.zov.smart.nova.common.mybatis.enums.DbEnum;
import com.zov.smart.nova.data.access.system.config.enums.ConfigTypeEnum;
import com.zov.smart.nova.data.access.system.dict.enums.DictStatusEnum;
import com.zov.smart.nova.data.access.system.log.enums.LoginFailureReasonEnum;
import com.zov.smart.nova.data.access.system.log.enums.LoginResultEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuStatusEnum;
import com.zov.smart.nova.data.access.system.menu.enums.MenuTypeEnum;
import com.zov.smart.nova.data.access.system.role.enums.RoleStatusEnum;
import com.zov.smart.nova.data.access.system.user.enums.UserStatusEnum;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.system.api.vo.enums.EntityEnumVO;
import com.zov.smart.nova.system.api.vo.enums.EnumInfoVO;
import com.zov.smart.nova.system.api.vo.enums.EnumItemVO;
import com.zov.smart.nova.system.biz.EnumBiz;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Runtime enum query implementation. */
@Service
public class EnumBizImpl implements EnumBiz {

    private final Map<String, List<EnumInfoVO>> registry = new LinkedHashMap<String, List<EnumInfoVO>>();

    public EnumBizImpl() {
        register("user", enumInfo("UserStatusEnum", "status", UserStatusEnum.values()));
        register("role", enumInfo("RoleStatusEnum", "status", RoleStatusEnum.values()));
        register("menu", enumInfo("MenuTypeEnum", "menuType", MenuTypeEnum.values()), enumInfo("MenuStatusEnum", "status", MenuStatusEnum.values()));
        register("dict", enumInfo("DictStatusEnum", "status", DictStatusEnum.values()));
        register("config", enumInfo("ConfigTypeEnum", "configType", ConfigTypeEnum.values()));
        register("loginLog", enumInfo("LoginResultEnum", "loginResult", LoginResultEnum.values()), enumInfo("LoginFailureReasonEnum", "failureReason", LoginFailureReasonEnum.values()));
        register("auditLog", enumInfo("AuditOperationTypeEnum", "operationType", AuditOperationTypeEnum.values()));
    }

    @Override
    public List<EntityEnumVO> listAllEntityEnums() {
        List<EntityEnumVO> result = new ArrayList<EntityEnumVO>();
        for (String entityName : registry.keySet()) {
            result.add(entityVO(entityName, registry.get(entityName)));
        }
        return result;
    }

    @Override
    public EntityEnumVO getEntityEnums(String entityName) {
        List<EnumInfoVO> enums = registry.get(entityName);
        if (enums == null) {
            throw new BusinessException(CommonErrorCode.COMMON_ENUM_NOT_FOUND);
        }
        return entityVO(entityName, enums);
    }

    private void register(String entityName, EnumInfoVO... enums) {
        List<EnumInfoVO> list = new ArrayList<EnumInfoVO>();
        for (EnumInfoVO item : enums) {
            list.add(item);
        }
        registry.put(entityName, list);
    }

    private EntityEnumVO entityVO(String entityName, List<EnumInfoVO> enums) {
        EntityEnumVO vo = new EntityEnumVO();
        vo.setEntityName(entityName);
        vo.setEnums(new ArrayList<EnumInfoVO>(enums));
        return vo;
    }

    private EnumInfoVO enumInfo(String enumName, String fieldName, DbEnum[] values) {
        EnumInfoVO vo = new EnumInfoVO();
        vo.setEnumName(enumName);
        vo.setFieldName(fieldName);
        List<EnumItemVO> items = new ArrayList<EnumItemVO>();
        int sort = 0;
        for (DbEnum value : values) {
            EnumItemVO item = new EnumItemVO();
            item.setCode(value.getCode());
            item.setLabel(value.getLabel());
            item.setSortOrder(sort++);
            item.setDisabled(Boolean.FALSE);
            items.add(item);
        }
        vo.setItems(items);
        return vo;
    }
}
