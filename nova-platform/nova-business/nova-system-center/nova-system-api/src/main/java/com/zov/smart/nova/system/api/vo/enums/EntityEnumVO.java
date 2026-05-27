package com.zov.smart.nova.system.api.vo.enums;

import java.util.ArrayList;
import java.util.List;

public class EntityEnumVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String entityName;

    private List<EnumInfoVO> enums;




            public String getEntityName() {
                return entityName;
            }

            public void setEntityName(String entityName) {
                this.entityName = entityName;
            }


            public List<EnumInfoVO> getEnums() {
                return enums;
            }

            public void setEnums(List<EnumInfoVO> enums) {
                this.enums = enums;
            }
     public EntityEnumVO(){this.enums=new ArrayList<EnumInfoVO>();} 
}
