package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.dict.enums.DictStatusEnum;

/**
 * 字典类型持久化对象，对应表 sys_dict_type。
 *
 * <p>本对象只承载持久化字段，不包含字典缓存刷新、唯一性校验或业务事务逻辑。</p>
 */
@TableName(value = "sys_dict_type", autoResultMap = true)
public class SysDictTypeDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    @TableField("dict_code")
    private String dictCode;

    @TableField("dict_name")
    private String dictName;

    @TableField(value = "status", typeHandler = EnumCodeTypeHandler.class)
    private DictStatusEnum status;

    @TableField("remark")
    private String remark;

    public String getDictCode() { return dictCode; }
    public void setDictCode(String dictCode) { this.dictCode = dictCode; }
    public String getDictName() { return dictName; }
    public void setDictName(String dictName) { this.dictName = dictName; }
    public DictStatusEnum getStatus() { return status; }
    public void setStatus(DictStatusEnum status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
