package com.zov.smart.nova.infra.web.config;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import com.zov.smart.nova.common.core.serializer.BigDecimalSerializer;
import com.zov.smart.nova.common.core.serializer.BigNumberSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@Configuration
public class JacksonConfig {




    // 自定义Timestamp序列化器（Timestamp对象 -> JSON字符串）
    public static class TimestampSerializer extends StdScalarSerializer<Timestamp> {
        private final SimpleDateFormat sdf;

        public TimestampSerializer() {
            super(Timestamp.class);
            this.sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }

        @Override
        public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (value == null) {
                gen.writeNull();
                return;
            }
            // 按指定格式序列化
            gen.writeString(sdf.format(value));
        }
    }

    /**
     * 配置全局的ObjectMapper，使用@Primary确保优先级最高
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        // 设置时区
        builder.timeZone(TimeZone.getTimeZone("GMT+8"));

        // 构建ObjectMapper
        ObjectMapper objectMapper = builder.build();

        // 禁用日期作为时间戳序列化（关键）
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 创建自定义模块，注册所有序列化/反序列化器
        SimpleModule customModule = new SimpleModule();

        // 注册时间类型序列化器/反序列化器
        customModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        customModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        customModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        customModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        customModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        customModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        // 注册Timestamp序列化器
        customModule.addSerializer(Timestamp.class, new TimestampSerializer());

        // 注册Long和BigDecimal序列化器（避免精度丢失）
        customModule.addSerializer(Long.class, BigNumberSerializer.INSTANCE);
        customModule.addSerializer(BigDecimal.class, new BigDecimalSerializer());

        // 将自定义模块注册到ObjectMapper
        objectMapper.registerModule(customModule);

        // 忽略未知属性
        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    //@Bean
    //@Order(Ordered.HIGHEST_PRECEDENCE)
    public Jackson2ObjectMapperBuilderCustomizer customJackson2() {
        return builder -> {

            // 自定义Timestamp序列化器（Timestamp对象 -> JSON字符串）
            builder.serializerByType(Timestamp.class, new TimestampSerializer());

            builder.serializerByType(LocalDateTime.class,
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            builder.serializerByType(LocalDate.class,
                    new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            builder.serializerByType(LocalTime.class,
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            builder.deserializerByType(LocalDateTime.class,
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            builder.deserializerByType(LocalDate.class,
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            builder.deserializerByType(LocalTime.class,
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
            // builder.serializationInclusion(JsonInclude.Include.NON_NULL);

            // 避免Long精度丢失，超过JS最大精度，使用String类型
            builder.serializerByType(Long.class, BigNumberSerializer.INSTANCE);

            // 避免BigDecimal精度丢失
            builder.serializerByType(BigDecimal.class, new BigDecimalSerializer());

            builder.failOnUnknownProperties(false);
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            //builder.serializerByType(I18nEnum.class, i18nEnumSerializer);
            //builder.serializerByType(I18nEnumString.class, i18nEnumStringSerializer);
        };
    }
}