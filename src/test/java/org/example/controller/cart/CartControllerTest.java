package org.example.controller.cart;

import static org.example.controller.TestObjects.cart;
import static org.example.controller.TestObjects.notFound;
import static org.example.controller.TestObjects.notFoundCode;
import static org.example.controller.TestObjects.receipt;
import static org.example.controller.TestObjects.userNotFound;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @SneakyThrows
  void getAllItemsInCart() {
    ResultActions result = mockMvc.perform(get("/api/cart/1")).andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(cart));
  }

  @Test
  @SneakyThrows
  void getAllItemsInCart_UserNotFound() {
    mockMvc
        .perform(get("/api/cart/-1"))
        .andExpectAll(status().isNotFound(), jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.code").value(userNotFound))
        .andExpect(jsonPath("$.status").value(notFoundCode))
        .andExpect(jsonPath("$.message").value(userNotFound));
  }

  @Test
  @SneakyThrows
  void addItemToCart() {
    ResultActions result =
        mockMvc
            .perform(
                post("/api/cart/add")
                    .param("item_id", "1")
                    .param("user_id", "2")
                    .param("count", "1"))
            .andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(receipt));
  }

  @Test
  @SneakyThrows
  void addItemToCart_UserNotFound() {
    mockMvc
        .perform(
            post("/api/cart/add").param("item_id", "1").param("user_id", "-1").param("count", "1"))
        .andExpectAll(status().isNotFound(), jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.code").value(userNotFound))
        .andExpect(jsonPath("$.status").value(notFoundCode))
        .andExpect(jsonPath("$.message").value(userNotFound));
  }

  @Test
  @SneakyThrows
  void addItemToCart_ItemNotFound() {
    mockMvc
        .perform(
            post("/api/cart/add").param("item_id", "-1").param("user_id", "1").param("count", "1"))
        .andExpectAll(status().isNotFound(), jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.code").value(notFound))
        .andExpect(jsonPath("$.status").value(notFoundCode))
        .andExpect(jsonPath("$.message").value(notFound));
  }

  @Test
  @SneakyThrows
  void deleteItemFromCart() {
    ResultActions result =
        mockMvc
            .perform(delete("/api/cart/delete").param("item_id", "1").param("user_id", "2"))
            .andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(receipt));
  }

  @Test
  @SneakyThrows
  void deleteItemFromCart_UserNotFound() {
    mockMvc
        .perform(delete("/api/cart/delete").param("item_id", "1").param("user_id", "-1"))
        .andExpectAll(status().isNotFound(), jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.code").value(userNotFound))
        .andExpect(jsonPath("$.status").value(notFoundCode))
        .andExpect(jsonPath("$.message").value(userNotFound));
  }
}
