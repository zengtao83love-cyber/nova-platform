package com.zov.smart.nova.system.api.contract;

import com.zov.smart.nova.system.api.command.config.CreateConfigCommand;
import com.zov.smart.nova.system.api.command.dict.CreateDictItemCommand;
import com.zov.smart.nova.system.api.command.dict.CreateDictTypeCommand;
import com.zov.smart.nova.system.api.vo.dict.LabelValue;
import com.zov.smart.nova.system.api.vo.enums.EntityEnumVO;
import com.zov.smart.nova.system.api.vo.enums.EnumInfoVO;
import com.zov.smart.nova.system.api.vo.enums.EnumItemVO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DictConfigLogEnumContractTest {
    @Test
    void dictAndConfigDefaultsFollowSpec() {
        assertEquals("ENABLED", new CreateDictTypeCommand().getStatus());
        CreateDictItemCommand item = new CreateDictItemCommand();
        assertEquals(Integer.valueOf(0), item.getSortOrder());
        assertEquals("ENABLED", item.getStatus());
        assertEquals("STRING", new CreateConfigCommand().getConfigType());
    }

    @Test
    void enumAndLabelValueCollectionsAreInitialized() {
        LabelValue labelValue = new LabelValue();
        assertEquals(Integer.valueOf(0), labelValue.getSortOrder());
        assertEquals(Boolean.FALSE, labelValue.getDisabled());
        assertNotNull(new EntityEnumVO().getEnums());
        assertNotNull(new EnumInfoVO().getItems());
        assertEquals(Boolean.FALSE, new EnumItemVO().getDisabled());
    }
}
