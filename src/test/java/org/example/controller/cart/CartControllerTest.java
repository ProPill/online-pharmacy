package org.example.controller.cart;

import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  @Test
  @SneakyThrows
  void getAllItemsInCart() {
  }

  @Test
  @SneakyThrows
  void getAllItemsInCart_UserNotFound() {
  }

  @Test
  @SneakyThrows
  void addItemToCart() {
  }

  @Test
  @SneakyThrows
  void addItemToCart_UserNotFound() {
  }

  @Test
  @SneakyThrows
  void addItemToCart_ItemNotFound() {
  }

  @Test
  @SneakyThrows
  void deleteItemFromCart() {
  }

  @Test
  @SneakyThrows
  void deleteItemFromCart_UserNotFound() {
  }
}
