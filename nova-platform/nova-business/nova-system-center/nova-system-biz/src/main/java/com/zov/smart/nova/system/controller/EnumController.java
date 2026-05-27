package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.vo.enums.EntityEnumVO;
import com.zov.smart.nova.system.biz.EnumBiz;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Runtime enum metadata HTTP API. */
@RestController
@RequestMapping("/api/system/enums")
public class EnumController {

    private final EnumBiz enumBiz;

    public EnumController(EnumBiz enumBiz) {
        this.enumBiz = enumBiz;
    }

    @GetMapping
    @RequirePermission(SystemPermissionConstants.ENUM_LIST)
    public Result<List<EntityEnumVO>> listAllEntityEnums() {
        return Result.success(enumBiz.listAllEntityEnums());
    }

    @GetMapping("/{entityName}")
    @RequirePermission(SystemPermissionConstants.ENUM_LIST)
    public Result<EntityEnumVO> getEntityEnums(@PathVariable("entityName") String entityName) {
        return Result.success(enumBiz.getEntityEnums(entityName));
    }
}
