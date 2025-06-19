package com.adminclient.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public final class JsonUtil {
    private static final ObjectMapper MAPPER = build();

    private JsonUtil() { }

    public static ObjectMapper mapper() {
        return MAPPER;
    }

    private static ObjectMapper build() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return om;
    }
}
