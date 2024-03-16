package org.example.controller.item;

import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.example.controller.TestObjects;
import org.example.controller.matchers.ItemJsonMatcher;
import org.example.dto.item.ItemDto;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.service.item.ItemQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.example.controller.TestObjects.*;
import static org.example.controller.matchers.ItemJsonMatcher.compare;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemQueryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MvcUtil mvcUtil;

//  @MockBean
//  private ItemQueryService itemQueryService;

  @Test
  @SneakyThrows
  void getAll() {
    ////when(itemQueryService.getAll()).thenReturn(items);
    ResultActions result = mockMvc
            .perform(get("/api/item/all"))
            .andExpectAll(status().isOk(), jsonPath("$", hasSize(2)));
    ItemDto[] resultDto = mvcUtil.readResponseValue(ItemDto[].class,result);
    assertArrayEquals(items, resultDto);
//    compare(result, "$[0]", items[0]);
//    compare(result, "$[1]", items[1]);
  }

  @Test
  @SneakyThrows
  void getAllReceiptAndNot() {
   /// when(itemQueryService.getAllReceiptAndNot()).thenReturn(List.of(receipt));
    ResultActions result = mockMvc
            .perform(get("/api/item/normal/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
   /// compare(result, "$[0]", receipt);
  }

  @Test
  @SneakyThrows
  void getAllItemsByDocId() {
   /// when(itemQueryService.getAllItemsByDocId(2L)).thenReturn(List.of(special));
    ResultActions result = mockMvc
            .perform(get("/api/item/doc/all?user_id=2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    //compare(result, "$[0]", special);
  }

  @Test
  @SneakyThrows
  void getAllItemsByTypeId() {
   /// when(itemQueryService.getAllItemsByTypeId(2L)).thenReturn(List.of(receipt));
    ResultActions result = mockMvc
            .perform(get("/api/item/type?type_id=2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    //compare(result, "$[0]", receipt);
  }

  @Test
  @SneakyThrows
  void getAllItemsBySpecId() {
    ///when(itemQueryService.getAllItemsBySpecId(1L)).thenReturn(List.of(special));
    ResultActions result = mockMvc
            .perform(get("/api/item/type/category?speciality_id=1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
   // compare(result, "$[0]", special);
  }
}
