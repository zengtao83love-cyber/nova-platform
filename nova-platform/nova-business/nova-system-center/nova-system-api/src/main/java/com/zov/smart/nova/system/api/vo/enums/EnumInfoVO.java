package com.zov.smart.nova.system.api.vo.enums;

import java.util.ArrayList;
import java.util.List;

public class EnumInfoVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String enumName;

    private String fieldName;

    private List<EnumItemVO> items;




            public String getEnumName() {
                return enumName;
            }

            public void setEnumName(String enumName) {
                this.enumName = enumName;
            }


            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }


            public List<EnumItemVO> getItems() {
                return items;
            }

            public void setItems(List<EnumItemVO> items) {
                this.items = items;
            }
     public EnumInfoVO(){this.items=new ArrayList<EnumItemVO>();} 
}
