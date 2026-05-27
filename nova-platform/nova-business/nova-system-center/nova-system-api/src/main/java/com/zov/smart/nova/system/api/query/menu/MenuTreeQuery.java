package com.zov.smart.nova.system.api.query.menu;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MenuTreeQuery implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Pattern(regexp = "^$|ENABLED|DISABLED", message = "菜单状态只能是ENABLED或DISABLED")
    private String status;

    @Min(value = 0, message = "显示标识只能为0或1")
    @Max(value = 1, message = "显示标识只能为0或1")
    private Integer visibleFlag;

    private Boolean includeButton;




            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }


            public Integer getVisibleFlag() {
                return visibleFlag;
            }

            public void setVisibleFlag(Integer visibleFlag) {
                this.visibleFlag = visibleFlag;
            }


            public Boolean getIncludeButton() {
                return includeButton;
            }

            public void setIncludeButton(Boolean includeButton) {
                this.includeButton = includeButton;
            }
         public MenuTreeQuery(){this.includeButton=Boolean.FALSE;} 
}
