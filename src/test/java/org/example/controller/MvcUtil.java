package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import java.io.UnsupportedEncodingException;

@Component
public class MvcUtil {
    private ObjectMapper objectMapper;

    public MvcUtil(ObjectMapper mapper) {
        objectMapper = mapper;
    }

    public <T> T readResponseValue(Class<T> clazz, ResultActions result) throws UnsupportedEncodingException, JsonProcessingException {
        String contentAsString = result.andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(contentAsString,clazz);

    }
}
