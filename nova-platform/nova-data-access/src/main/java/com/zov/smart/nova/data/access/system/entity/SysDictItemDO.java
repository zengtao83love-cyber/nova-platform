package com.zov.smart.nova.data.access.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zov.smart.nova.common.mybatis.entity.BaseDO;
import com.zov.smart.nova.common.mybatis.handler.EnumCodeTypeHandler;
import com.zov.smart.nova.data.access.system.dict.enums.DictStatusEnum;

/**
 * 字典项持久化对象，对应表 sys_dict_item。
 */
@TableName(value = "sys_dict_item", autoResultMap = true)
public class SysDictItemDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    @TableField("dict_code")
    private String dictCode;

    @TableField("item_label")
    private String itemLabel;

    @TableField("item_value")
    private String itemValue;

    @TableField("sort_order")
    private Integer sortOrder;

    @TableField(value = "status", typeHandler = EnumCodeTypeHandler.class)
    private DictStatusEnum status;

    @TableField("remark")
    private String remark;

    public String getDictCode() { return dictCode; }
    public void setDictCode(String dictCode) { this.dictCode = dictCode; }
    public String getItemLabel() { return itemLabel; }
    public void setItemLabel(String itemLabel) { this.itemLabel = itemLabel; }
    public String getItemValue() { return itemValue; }
    public void setItemValue(String itemValue) { this.itemValue = itemValue; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public DictStatusEnum getStatus() { return status; }
    public void setStatus(DictStatusEnum status) { this.status = status; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
