package com.khoa.CineFlex.controller;

import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class ControllerTestHelper {
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
