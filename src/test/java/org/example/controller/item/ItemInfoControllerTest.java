package org.example.controller.item;

import static org.example.controller.TestObjects.receipt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.service.item.ItemInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ItemInfoControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ItemInfoService itemInfoService;

  @Test
  @SneakyThrows
  void getItemInfo() {
    //when(itemInfoService.getItemInfo(1L)).thenReturn(receipt);
    mockMvc.perform(get("/api/item/info/1")).andExpect(status().isOk());
  }
}
