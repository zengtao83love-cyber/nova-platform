package com.zov.smart.nova.system.api.command.dict;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateDictItemCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "字典项ID不能为空")
    private Long id;

    @NotBlank(message = "字典项标签不能为空")
    @Size(max = 128, message = "字典项标签长度不能超过128")
    private String itemLabel;

    @NotBlank(message = "字典项值不能为空")
    @Size(max = 128, message = "字典项值长度不能超过128")
    private String itemValue;

    @Min(value = 0, message = "排序不能小于0")
    private Integer sortOrder;

    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "字典项状态只能是ENABLED或DISABLED")
    private String status;

    @Size(max = 255, message = "备注长度不能超过255")
    private String remark;




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }


            public String getItemLabel() {
                return itemLabel;
            }

            public void setItemLabel(String itemLabel) {
                this.itemLabel = itemLabel;
            }


            public String getItemValue() {
                return itemValue;
            }

            public void setItemValue(String itemValue) {
                this.itemValue = itemValue;
            }


            public Integer getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(Integer sortOrder) {
                this.sortOrder = sortOrder;
            }


            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }


            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }
     public UpdateDictItemCommand(){this.sortOrder=0;} 
}
