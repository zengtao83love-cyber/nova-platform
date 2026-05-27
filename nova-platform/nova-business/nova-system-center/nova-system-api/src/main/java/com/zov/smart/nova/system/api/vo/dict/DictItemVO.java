package com.zov.smart.nova.system.api.vo.dict;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class DictItemVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String dictCode;

    private String itemLabel;

    private String itemValue;

    private Integer sortOrder;

    private String status;

    private String remark;

    private LocalDateTime createdAt;

    private Long updatedBy;

    private LocalDateTime updatedAt;


     public DictItemVO(){this.sortOrder=0;this.status="ENABLED";} 
}
