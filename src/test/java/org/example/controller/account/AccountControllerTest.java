package org.example.controller.account;

import static org.example.controller.TestObjects.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
class AccountControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  @Test
  @SneakyThrows
  void loginSuccess() {
    ResultActions result =
        mockMvc
            .perform(
                post("/api/accounts/login")
                    .param("phone", "+79260567450")
                    .param("password", "123456"))
            .andExpect(status().isOk());
    UserAccountDto resultDto = mvcUtil.readResponseValue(UserAccountDto.class, result);
    assertEquals(simpleUser, resultDto);
  }

  @Test
  @SneakyThrows
  void registerSuccess() {
    mockMvc
        .perform(
            post("/api/accounts/registry")
                .param("phone", phoneStr)
                .param("password", "1234565")
                .param("full_name", fullNameStr))
        .andExpectAll(
            status().isOk(),
            jsonPath("$.id").exists(),
            jsonPath("$.full_name").value(fullNameStr),
            jsonPath("$.phone").value(phoneStr),
            jsonPath("$.role.id").value(simpleUserRole.id()),
            jsonPath("$.role.name").value(simpleUserRole.name()),
            jsonPath("$.speciality").isEmpty());
  }

  @Test
  @SneakyThrows
  void registerInvalidFullName() {
    mockMvc
        .perform(
            post("/api/accounts/registry")
                .param("phone", phoneStr)
                .param("password", "1234565")
                .param("full_name", "Пупкин Вася 1"))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.*", hasSize(3)),
            jsonPath("$.code").value(invalidFio),
            jsonPath("$.status").value(badRequest),
            jsonPath("$.message").value(invalidFio));
  }

  @Test
  @SneakyThrows
  void loginFailed() {
    mockMvc
        .perform(
            post("/api/accounts/login").param("phone", "+79260567450").param("password", "1234565"))
        .andExpectAll(
            status().isBadRequest(),
            jsonPath("$.*", hasSize(3)),
            jsonPath("$.code").value(wrongLoginPassword),
            jsonPath("$.status").value(badRequest),
            jsonPath("$.message").value(wrongLoginPassword));
  }

  @Test
  @SneakyThrows
  void logoutSuccess() {
    ResultActions result =
        mockMvc
            .perform(post("/api/accounts/logout").param("user_id", "-1"))
            .andExpect(status().isOk());
    UserAccountDto resultDto = mvcUtil.readResponseValue(UserAccountDto.class, result);
    assertEquals(simpleUser, resultDto);
  }

  @Test
  @SneakyThrows
  void logoutFailed() {
    mockMvc
        .perform(post("/api/accounts/logout").param("user_id", "-6"))
        .andExpectAll(
            status().isNotFound(),
            jsonPath("$.*", hasSize(3)),
            jsonPath("$.code").value(userNotFound),
            jsonPath("$.status").value(notFoundCode),
            jsonPath("$.message").value(userNotFound));
  }
}
