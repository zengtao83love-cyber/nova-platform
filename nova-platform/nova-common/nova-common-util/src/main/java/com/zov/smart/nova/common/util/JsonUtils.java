package com.zov.smart.nova.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Jackson JSON 工具类。
 */
public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    public static String toJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Failed to serialize object to JSON", ex);
        }
    }

    public static <T> T parseObject(String json, Class<T> targetType) {
        if (isBlank(json)) {
            return null;
        }
        if (targetType == null) {
            throw new IllegalArgumentException("targetType cannot be null");
        }
        try {
            return OBJECT_MAPPER.readValue(json, targetType);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to parse JSON as " + targetType.getName(), ex);
        }
    }

    public static <T> List<T> parseList(String json, Class<T> elementType) {
        if (isBlank(json)) {
            return Collections.emptyList();
        }
        if (elementType == null) {
            throw new IllegalArgumentException("elementType cannot be null");
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, elementType);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to parse JSON list as " + elementType.getName(), ex);
        }
    }

    public static ObjectMapper objectMapper() {
        return OBJECT_MAPPER;
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
