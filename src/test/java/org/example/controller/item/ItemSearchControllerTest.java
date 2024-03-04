package org.example.controller.item;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.service.item.ItemSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ItemSearchControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ItemSearchService itemSearchService;

  @Test
  void searchItems() throws Exception {
    mockMvc.perform(get("/api/item/search_result?search=example"))
        .andExpect(status().isOk());
  }
}
