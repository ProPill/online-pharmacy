package org.example.controller.user;

import static org.example.controller.TestObjects.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.example.dto.user.UserAccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  @Test
  @SneakyThrows
  void getUserInfoSuccess() {
    ResultActions result = mockMvc.perform(get("/api/info/-1")).andExpect(status().isOk());
    UserAccountDto resultDto = mvcUtil.readResponseValue(UserAccountDto.class, result);
    assertEquals(simpleUser, resultDto);
  }

  @Test
  @SneakyThrows
  void getUserInfoFailed() {
    mockMvc
        .perform(get("/api/info/-6"))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.*", hasSize(3)),
            jsonPath("$.code").value(userNotFound),
            jsonPath("$.status").value(notFoundCode),
            jsonPath("$.message").value(userNotFound));
  }
}
