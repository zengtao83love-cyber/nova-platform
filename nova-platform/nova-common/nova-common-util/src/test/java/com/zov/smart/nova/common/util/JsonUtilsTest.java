package com.zov.smart.nova.common.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {
    @Test
    void shouldSerializeAndDeserializeObject() {
        DemoPayload payload = new DemoPayload();
        payload.setName("nova");
        payload.setEnabled(true);

        String json = JsonUtils.toJson(payload);
        DemoPayload parsed = JsonUtils.parseObject(json, DemoPayload.class);

        assertTrue(json.contains("nova"));
        assertEquals("nova", parsed.getName());
        assertTrue(parsed.isEnabled());
    }

    @Test
    void shouldParseList() {
        String json = JsonUtils.toJson(Arrays.asList("A", "B"));

        List<String> values = JsonUtils.parseList(json, String.class);

        assertEquals(Arrays.asList("A", "B"), values);
    }

    public static class DemoPayload {
        private String name;
        private boolean enabled;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
