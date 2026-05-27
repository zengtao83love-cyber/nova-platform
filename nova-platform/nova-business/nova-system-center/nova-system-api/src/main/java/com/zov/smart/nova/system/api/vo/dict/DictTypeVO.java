package com.zov.smart.nova.system.api.vo.dict;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DictTypeVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String dictCode;

    private String dictName;

    private String status;

    private String remark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;




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


            public LocalDateTime getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
            }


            public LocalDateTime getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
            }
     public DictTypeVO(){this.status="ENABLED";} 
}
