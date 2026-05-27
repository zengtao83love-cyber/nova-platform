package com.zov.smart.nova.system.api.vo.dict;



public class LabelValue implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String label;

    private String value;

    private Integer sortOrder;

    private Boolean disabled;




            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }


            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
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
         public LabelValue(){this.sortOrder=0;this.disabled=Boolean.FALSE;} 
}
