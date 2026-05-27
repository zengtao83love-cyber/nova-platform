package com.zov.smart.nova.system.api.dto.dict;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DictItemDTO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String dictCode;

    private String itemLabel;

    private String itemValue;

    private Integer sortOrder;

    private String status;

    private String remark;




            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }


            public String getDictCode() {
                return dictCode;
            }

            public void setDictCode(String dictCode) {
                this.dictCode = dictCode;
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
     public DictItemDTO(){this.sortOrder=0;this.status="ENABLED";} 
}
