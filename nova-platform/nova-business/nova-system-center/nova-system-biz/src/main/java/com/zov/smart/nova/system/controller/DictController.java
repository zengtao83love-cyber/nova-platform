package com.zov.smart.nova.system.controller;

import com.zov.smart.nova.common.core.response.Result;
import com.zov.smart.nova.common.model.PageResult;
import com.zov.smart.nova.infra.audit.annotation.OperationLog;
import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;
import com.zov.smart.nova.infra.guard.annotation.RepeatSubmitGuard;
import com.zov.smart.nova.infra.security.annotation.RequirePermission;
import com.zov.smart.nova.system.api.command.dict.CreateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.CreateDictTypeCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.UpdateDictTypeCommand;
import com.zov.smart.nova.system.api.constant.SystemPermissionConstants;
import com.zov.smart.nova.system.api.query.dict.DictItemPageQuery;
import com.zov.smart.nova.system.api.query.dict.DictTypePageQuery;
import com.zov.smart.nova.system.api.vo.dict.DictItemVO;
import com.zov.smart.nova.system.api.vo.dict.DictTypeVO;
import com.zov.smart.nova.system.api.vo.dict.LabelValue;
import com.zov.smart.nova.system.biz.DictBiz;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/** Dictionary HTTP API. */
@Validated
@RestController
@RequestMapping("/api/system")
public class DictController {

    private final DictBiz dictBiz;

    public DictController(DictBiz dictBiz) {
        this.dictBiz = dictBiz;
    }

    @GetMapping("/dict-types")
    @RequirePermission(SystemPermissionConstants.DICT_LIST)
    public Result<PageResult<DictTypeVO>> pageDictTypes(@Valid DictTypePageQuery query) {
        return Result.success(dictBiz.pageDictTypes(query));
    }

    @PostMapping("/dict-types")
    @RepeatSubmitGuard
    @OperationLog(name = "新增字典类型", type = AuditOperationTypeEnum.CREATE)
    @RequirePermission(SystemPermissionConstants.DICT_CREATE)
    public Result<Long> createDictType(@Valid @RequestBody CreateDictTypeCommand command) {
        return Result.success(dictBiz.createDictType(command));
    }

    @PutMapping("/dict-types/{id}")
    @RepeatSubmitGuard
    @OperationLog(name = "修改字典类型", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.DICT_UPDATE)
    public Result<Void> updateDictType(@PathVariable("id") Long id, @Valid @RequestBody UpdateDictTypeCommand command) {
        command.setId(id);
        dictBiz.updateDictType(command);
        return Result.success(null);
    }

    @DeleteMapping("/dict-types/{id}")
    @OperationLog(name = "删除字典类型", type = AuditOperationTypeEnum.DELETE)
    @RequirePermission(SystemPermissionConstants.DICT_DELETE)
    public Result<Void> deleteDictType(@PathVariable("id") Long id) {
        dictBiz.deleteDictType(id);
        return Result.success(null);
    }

    @GetMapping("/dict-items")
    @RequirePermission(SystemPermissionConstants.DICT_LIST)
    public Result<PageResult<DictItemVO>> pageDictItems(@Valid DictItemPageQuery query) {
        return Result.success(dictBiz.pageDictItems(query));
    }

    @GetMapping("/dict-items/options")
    @RequirePermission(SystemPermissionConstants.DICT_LIST)
    public Result<List<LabelValue>> options(@RequestParam("dictCode") String dictCode) {
        return Result.success(dictBiz.listOptions(dictCode));
    }

    @PostMapping("/dict-items")
    @RepeatSubmitGuard
    @OperationLog(name = "新增字典项", type = AuditOperationTypeEnum.CREATE)
    @RequirePermission(SystemPermissionConstants.DICT_CREATE)
    public Result<Long> createDictItem(@Valid @RequestBody CreateDictItemCommand command) {
        return Result.success(dictBiz.createDictItem(command));
    }

    @PutMapping("/dict-items/{id}")
    @RepeatSubmitGuard
    @OperationLog(name = "修改字典项", type = AuditOperationTypeEnum.UPDATE)
    @RequirePermission(SystemPermissionConstants.DICT_UPDATE)
    public Result<Void> updateDictItem(@PathVariable("id") Long id, @Valid @RequestBody UpdateDictItemCommand command) {
        command.setId(id);
        dictBiz.updateDictItem(command);
        return Result.success(null);
    }

    @DeleteMapping("/dict-items/{id}")
    @OperationLog(name = "删除字典项", type = AuditOperationTypeEnum.DELETE)
    @RequirePermission(SystemPermissionConstants.DICT_DELETE)
    public Result<Void> deleteDictItem(@PathVariable("id") Long id) {
        dictBiz.deleteDictItem(id);
        return Result.success(null);
    }
}
