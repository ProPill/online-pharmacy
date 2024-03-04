package org.example.controller.item;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.service.item.ItemSearchService;
import org.junit.jupiter.api.BeforeEach;
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

  private Item item1;

  @BeforeEach
  void setUp() {
    Type type = new Type();
    type.setId(1L);
    type.setName("Test Type");

    Speciality speciality = new Speciality();
    speciality.setId(1L);
    speciality.setName("Test Speciality");

    item1 = new Item();
    item1.setId(1L);
    item1.setName("example");
    item1.setPrice(100.0);
    item1.setManufacturer("Test Manufacturer");
    item1.setInfo("Test Info");
    item1.setPictureUrl("test.jpg");
    item1.setType(type);
    item1.setSpeciality(speciality);
  }

  @Test
  void searchItems() throws Exception {
    when(itemSearchService.searchItemByName("example")).thenReturn(List.of(item1));
    mockMvc.perform(get("/api/item/search_result?search=example"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));;
  }
}
