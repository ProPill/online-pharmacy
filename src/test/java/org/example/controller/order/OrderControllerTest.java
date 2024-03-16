package org.example.controller.order;

import static org.example.controller.TestObjects.orders;
import static org.example.controller.TestObjects.receipt;
import static org.example.controller.TestObjects.special;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.example.dto.order.OrderDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  @Test
  @SneakyThrows
  void getAllByUserId() {
    ResultActions result =
        mockMvc
            .perform(get("/api/orders?user_id=1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)));
    OrderDto[] resultDto = mvcUtil.readResponseValue(OrderDto[].class, result);
    assertArrayEquals(orders, resultDto);
  }

  @Test
  void placeOrder() {
  }
}