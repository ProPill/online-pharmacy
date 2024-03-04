package org.example.controller.item;

import static org.junit.jupiter.api.Assertions.*;

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
  void getAll() {
  }

  @Test
  void getAllReceiptAndNot() {
  }

  @Test
  void getAllItemsByDocId() {
  }

  @Test
  void getAllItemsByTypeId() {
  }

  @Test
  void getAllItemsBySpecId() {
  }
}