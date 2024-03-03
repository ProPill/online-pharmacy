package org.example.controller.pharmacy;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import org.example.entities.pharmacy.Pharmacy;
import org.example.service.pharmacy.PharmacyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PharmacyControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private PharmacyService pharmacyService;

  @Test
  void testGetAll() throws Exception {
    List<Pharmacy> pharmacies = Arrays.asList(new Pharmacy(), new Pharmacy());
    when(pharmacyService.getAll()).thenReturn(pharmacies);
    mockMvc
        .perform(get("/api/pharmacy/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  void testGetAllPharmaciesByItemId() throws Exception {
    Long itemId = 1L;
    List<Pharmacy> pharmacies = Arrays.asList(new Pharmacy(), new Pharmacy());
    when(pharmacyService.getAllByItemId(itemId)).thenReturn(pharmacies);
    mockMvc
        .perform(get("/api/pharmacy/item?item_id=1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }
}
