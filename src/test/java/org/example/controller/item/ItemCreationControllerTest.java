package org.example.controller.item;

import static org.junit.jupiter.api.Assertions.*;

import org.example.service.item.ItemCreationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ItemCreationControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ItemCreationService itemCreationService;

  @Test
  void addItemByAdmin() throws Exception {
  }
}