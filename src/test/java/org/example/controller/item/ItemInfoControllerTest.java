package org.example.controller.item;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  private Item expectedItem;

  @BeforeEach
  void setUp() {
    Type type = new Type();
    type.setId(1L);
    type.setName("Test Type");

    Speciality speciality = new Speciality();
    speciality.setId(1L);
    speciality.setName("Test Speciality");

    expectedItem = new Item();
    expectedItem.setId(1L);
    expectedItem.setName("Test Item");
    expectedItem.setPrice(100.0);
    expectedItem.setManufacturer("Test Manufacturer");
    expectedItem.setInfo("Test Info");
    expectedItem.setPictureUrl("test.jpg");
    expectedItem.setType(type);
    expectedItem.setSpeciality(speciality);
  }

  @Test
  void getItemInfo() throws Exception {
    when(itemInfoService.getItemInfo(1L)).thenReturn(expectedItem);
    mockMvc.perform(get("/api/item/info/1")).andExpect(status().isOk());
  }
}
