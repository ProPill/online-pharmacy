package org.example.controller.item;

import static org.example.controller.TestObjects.receipt;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.example.dto.item.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ItemSearchControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  @Test
  @SneakyThrows
  void searchItems() {
    ResultActions result =
        mockMvc
            .perform(get("/api/item/search_result?search=Афобазол"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    ItemDto[] resultDto = mvcUtil.readResponseValue(ItemDto[].class, result);
    assertArrayEquals(new ItemDto[] {receipt}, resultDto);
  }
}
