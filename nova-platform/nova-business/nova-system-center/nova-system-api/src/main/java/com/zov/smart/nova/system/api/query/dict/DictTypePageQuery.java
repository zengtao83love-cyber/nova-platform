package com.zov.smart.nova.system.api.query.dict;

import com.zov.smart.nova.common.model.PageParam;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DictTypePageQuery extends PageParam {
    private static final long serialVersionUID = 1L;
    @Size(max = 64) private String dictCode;
    @Size(max = 64) private String dictName;
    @Pattern(regexp = "^$|ENABLED|DISABLED") private String status;
    public String getDictCode() { return dictCode; }
    public void setDictCode(String dictCode) { this.dictCode = dictCode; }
    public String getDictName() { return dictName; }
    public void setDictName(String dictName) { this.dictName = dictName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
