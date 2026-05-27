package com.zov.smart.nova.system.api.vo.enums;



public class EnumItemVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String code;

    private String label;

    private Integer sortOrder;

    private Boolean disabled;




            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }


            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }


            public Integer getSortOrder() {
                return sortOrder;
            }

            public void setSortOrder(Integer sortOrder) {
                this.sortOrder = sortOrder;
            }


            public Boolean getDisabled() {
                return disabled;
            }

            public void setDisabled(Boolean disabled) {
                this.disabled = disabled;
            }
         public EnumItemVO(){this.sortOrder=0;this.disabled=Boolean.FALSE;} 
}
