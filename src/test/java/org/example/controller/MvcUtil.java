package org.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class MvcUtil {
  private final ObjectMapper objectMapper;

  public MvcUtil(ObjectMapper mapper) {
    objectMapper = mapper;
  }

  public <T> T readResponseValue(Class<T> clazz, ResultActions result)
      throws UnsupportedEncodingException, JsonProcessingException {
    String contentAsString =
        result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    return objectMapper.readValue(contentAsString, clazz);
  }

  public void assertContentEquals(ResultActions result, String expectedJson) throws Exception {
    String content = result.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    byte[] utf8Json = expectedJson.getBytes(StandardCharsets.UTF_8);
    assertEquals(new String(utf8Json, StandardCharsets.UTF_8), content);
  }
}
