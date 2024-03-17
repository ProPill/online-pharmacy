package org.example.controller.cart;

import static org.example.controller.TestObjects.cartSecUser;
import static org.example.controller.TestObjects.cartTest2User;
import static org.example.controller.TestObjects.cartTestUser;
import static org.example.controller.TestObjects.creationDate;
import static org.example.controller.TestObjects.creationDateStr;
import static org.example.controller.TestObjects.deliveryDate;
import static org.example.controller.TestObjects.deliveryDateStr;
import static org.example.controller.TestObjects.notFound;
import static org.example.controller.TestObjects.notFoundCode;
import static org.example.controller.TestObjects.receipt;
import static org.example.controller.TestObjects.special;
import static org.example.controller.TestObjects.sumPrice;
import static org.example.controller.TestObjects.test2User;
import static org.example.controller.TestObjects.userNotFound;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
class CartControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @SneakyThrows
  void getAllItemsInCart() {
    ResultActions result = mockMvc.perform(get("/api/cart/-2")).andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(cartSecUser));
  }

  @Test
  @SneakyThrows
  void getAllItemsInCart_UserNotFound() {
    mockMvc
        .perform(get("/api/cart/-6"))
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
                    .param("item_id", "-1")
                    .param("user_id", "-2")
                    .param("count", "1"))
            .andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(receipt));
  }

  @Test
  @SneakyThrows
  void addItemToCart_UserNotFound() {
    mockMvc
        .perform(
            post("/api/cart/add").param("item_id", "-1").param("user_id", "-6").param("count", "1"))
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
            post("/api/cart/add").param("item_id", "1").param("user_id", "-1").param("count", "1"))
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
            .perform(delete("/api/cart/delete").param("item_id", "-2").param("user_id", "-2"))
            .andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(special));
  }

  @Test
  @SneakyThrows
  void deleteItemFromCart_UserNotFound() {
    mockMvc
        .perform(delete("/api/cart/delete").param("item_id", "-1").param("user_id", "-6"))
        .andExpectAll(status().isNotFound(), jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.code").value(userNotFound))
        .andExpect(jsonPath("$.status").value(notFoundCode))
        .andExpect(jsonPath("$.message").value(userNotFound));
  }

  // тут начинаются сложные тесты

  // тут УДАЛЯЕТСЯ из корзины, при перезапуске тестов нужно обновить тестовую бд !!
  // просмотр корзины пользователя и размещение заказа
  @Test
  @SneakyThrows
  void getCartInfoAndPlaceOrder() {
    ResultActions result = mockMvc.perform(get("/api/cart/-4")).andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(cartTestUser));

    mockMvc
        .perform(
            post("/api/order")
                .param("user_id", String.valueOf(-4L))
                .param("creation_date", creationDate.toString())
                .param("delivery_date", deliveryDate.toString())
                .param("sum_price", String.valueOf(sumPrice))
                .param("pharmacy_id", "-1")
                .param("items", "-2"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user_id").value(-4L))
        .andExpect(jsonPath("$.creation_date").value(creationDateStr))
        .andExpect(jsonPath("$.delivery_date").value(deliveryDateStr))
        .andExpect(jsonPath("$.sum_price").value(String.valueOf(sumPrice)))
        .andExpect(jsonPath("$.pharmacy.id").value("-1"));
  }

  // добавление препарата в корзину и его удаление из корзины
  @Test
  @SneakyThrows
  void addItemIntoCartAndDeleteItemFromCart() {
    ResultActions result =
        mockMvc
            .perform(
                post("/api/cart/add")
                    .param("item_id", "-1")
                    .param("user_id", "-2")
                    .param("count", "1"))
            .andExpect(status().isOk());
    mvcUtil.assertContentEquals(result, objectMapper.writeValueAsString(receipt));

    ResultActions result2 =
        mockMvc
            .perform(delete("/api/cart/delete").param("item_id", "-1").param("user_id", "-2"))
            .andExpect(status().isOk());
    mvcUtil.assertContentEquals(result2, objectMapper.writeValueAsString(receipt));
  }

  // аутентификация пользователя, добавление товара в корзину и просмотр корзины
  @Test
  @SneakyThrows
  void authUserAddItemIntoCartAndCheckCart() {
    ResultActions result =
        mockMvc
            .perform(
                post("/api/accounts/login")
                    .param("phone", "+79510367458")
                    .param("password", "12345"))
            .andExpect(status().isOk());
    UserAccountDto resultDto = mvcUtil.readResponseValue(UserAccountDto.class, result);
    assertEquals(test2User, resultDto);

    ResultActions result2 =
        mockMvc
            .perform(
                post("/api/cart/add")
                    .param("item_id", "-1")
                    .param("user_id", "-5")
                    .param("count", "2"))
            .andExpect(status().isOk());
    mvcUtil.assertContentEquals(result2, objectMapper.writeValueAsString(receipt));

    ResultActions result3 = mockMvc.perform(get("/api/cart/-5")).andExpect(status().isOk());
    mvcUtil.assertContentEquals(result3, objectMapper.writeValueAsString(cartTest2User));
  }
}
