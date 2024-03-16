package org.example.controller.order;

import static org.example.controller.TestObjects.creationDate;
import static org.example.controller.TestObjects.creationDateStr;
import static org.example.controller.TestObjects.deliveryDate;
import static org.example.controller.TestObjects.deliveryDateStr;
import static org.example.controller.TestObjects.orders;
import static org.example.controller.TestObjects.sumPrice;
import static org.example.controller.TestObjects.userId;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
  @SneakyThrows
  void placeOrder() {
    mockMvc.perform(post("/api/order")
            .param("user_id", String.valueOf(userId))
            .param("creation_date", creationDate.toString())
            .param("delivery_date", deliveryDate.toString())
            .param("sum_price", String.valueOf(sumPrice))
            .param("pharmacy_id", "1")
            .param("items", "1")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user_id").value(userId))
        .andExpect(jsonPath("$.creation_date").value(creationDateStr))
        .andExpect(jsonPath("$.delivery_date").value(deliveryDateStr))
        .andExpect(jsonPath("$.sum_price").value(String.valueOf(sumPrice)))
        .andExpect(jsonPath("$.pharmacy.id").value("1"));
  }
}
