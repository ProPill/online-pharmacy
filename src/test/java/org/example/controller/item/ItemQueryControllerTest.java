package org.example.controller.item;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
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

@SpringBootTest
@AutoConfigureMockMvc
class ItemQueryControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ItemQueryService itemQueryService;

  private Item receipt;
  private Item common;
  private Item special;

  @BeforeEach
  void setUp() {
    Type type = new Type();
    type.setId(1L);
    type.setName("receipt");

    Type type1 = new Type();
    type.setId(2L);
    type.setName("common");

    Type type2 = new Type();
    type.setId(3L);
    type.setName("special");

    Speciality speciality = new Speciality();
    speciality.setId(1L);
    speciality.setName("Test Speciality");

    receipt = new Item();
    receipt.setId(1L);
    receipt.setName("example");
    receipt.setPrice(100.0);
    receipt.setManufacturer("Test Manufacturer");
    receipt.setInfo("Test Info");
    receipt.setPictureUrl("test.jpg");
    receipt.setType(type);
    receipt.setSpeciality(null);

    common = new Item();
    common.setId(2L);
    common.setName("example2");
    common.setPrice(200.0);
    common.setManufacturer("Test Manufacturer2");
    common.setInfo("Test Info2");
    common.setPictureUrl("test2.jpg");
    common.setType(type1);
    common.setSpeciality(null);

    special = new Item();
    special.setId(3L);
    special.setName("example3");
    special.setPrice(200.0);
    special.setManufacturer("Test Manufacturer2");
    special.setInfo("Test Info2");
    special.setPictureUrl("test3.jpg");
    special.setType(type2);
    special.setSpeciality(speciality);
  }

  @Test
  void getAll() throws Exception {
    when(itemQueryService.getAll()).thenReturn(List.of(receipt, common, special));
    mockMvc.perform(get("/api/item/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  void getAllReceiptAndNot() throws Exception {
    when(itemQueryService.getAllReceiptAndNot()).thenReturn(List.of(receipt, common));
    mockMvc.perform(get("/api/item/normal/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void getAllItemsByDocId() throws Exception {
    when(itemQueryService.getAllItemsByDocId(1L)).thenReturn(List.of(special));
    mockMvc.perform(get("/api/item/doc/all?user_id=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  void getAllItemsByTypeId() throws Exception {
    when(itemQueryService.getAllItemsByTypeId(1L)).thenReturn(List.of(receipt));
    mockMvc.perform(get("/api/item/type?type_id=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }

  @Test
  void getAllItemsBySpecId() throws Exception {
    when(itemQueryService.getAllItemsBySpecId(1L)).thenReturn(List.of(special));
    mockMvc.perform(get("/api/item/type/category?speciality_id=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)));
  }
}
