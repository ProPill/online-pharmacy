package org.example.controller.item;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.service.item.ItemQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ItemQueryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ItemQueryService itemQueryService;

  @Test
  void getAll() throws Exception {
    mockMvc.perform(get("/api/item/all")).andExpect(status().isOk());
  }

  @Test
  void getAllReceiptAndNot() throws Exception {
    mockMvc.perform(get("/api/item/normal/all")).andExpect(status().isOk());
  }

  @Test
  void getAllItemsByDocId() throws Exception {
    mockMvc.perform(get("/api/item/doc/all?user_id=123")).andExpect(status().isOk());
  }

  @Test
  void getAllItemsByTypeId() throws Exception {
    mockMvc.perform(get("/api/item/type?type_id=456")).andExpect(status().isOk());
  }

  @Test
  void getAllItemsBySpecId() throws Exception {
    mockMvc.perform(get("/api/item/type/category?speciality_id=789")).andExpect(status().isOk());
  }
}
