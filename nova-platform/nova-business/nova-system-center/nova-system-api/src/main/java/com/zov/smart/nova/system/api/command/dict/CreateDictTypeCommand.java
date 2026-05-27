package com.zov.smart.nova.system.api.command.dict;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CreateDictTypeCommand implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "字典编码不能为空")
    @Size(max = 64, message = "字典编码长度不能超过64")
    @Pattern(regexp = "^[a-zA-Z0-9_.:-]+$", message = "字典编码格式不正确")
    private String dictCode;

    @NotBlank(message = "字典名称不能为空")
    @Size(max = 64, message = "字典名称长度不能超过64")
    private String dictName;

    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "字典状态只能是ENABLED或DISABLED")
    private String status;

    @Size(max = 255, message = "备注长度不能超过255")
    private String remark;




            public String getDictCode() {
                return dictCode;
            }

            public void setDictCode(String dictCode) {
                this.dictCode = dictCode;
            }


            public String getDictName() {
                return dictName;
            }

            public void setDictName(String dictName) {
                this.dictName = dictName;
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
     public CreateDictTypeCommand(){this.status="ENABLED";} 
}
