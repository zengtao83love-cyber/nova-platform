package com.zov.smart.nova.system.api.query.dict;

import com.zov.smart.nova.common.model.PageParam;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DictItemPageQuery extends PageParam {
    private static final long serialVersionUID = 1L;
    @NotBlank @Size(max = 64) private String dictCode;
    @Size(max = 128) private String itemLabel;
    @Pattern(regexp = "^$|ENABLED|DISABLED") private String status;
    public String getDictCode() { return dictCode; }
    public void setDictCode(String dictCode) { this.dictCode = dictCode; }
    public String getItemLabel() { return itemLabel; }
    public void setItemLabel(String itemLabel) { this.itemLabel = itemLabel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
