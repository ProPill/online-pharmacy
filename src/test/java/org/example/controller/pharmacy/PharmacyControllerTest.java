package org.example.controller.pharmacy;

import lombok.SneakyThrows;
import org.example.entities.pharmacy.Pharmacy;
import org.example.service.pharmacy.PharmacyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PharmacyControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PharmacyService pharmacyService;

  private Pharmacy firstPharmacy;
  private Pharmacy secondPharmacy;
  private List<Pharmacy> pharmacies;

  @BeforeEach
  void setUp() {
    firstPharmacy = new Pharmacy(1L, "Здоровье",
            "ул. Ленина, 10",
            "Пн-Пт: 9:00-18:00, Сб: 10:00-15:00",
            "+7 (123) 456-7890", new ArrayList<>(), new ArrayList<>());
    secondPharmacy = new Pharmacy(2L, "Фармация", "пр. Победы, 25",
            "Пн-Вс: 8:00-22:00",
            "+7 (987) 654-3210", new ArrayList<>(), new ArrayList<>());
    pharmacies = Arrays.asList(firstPharmacy, secondPharmacy);

  }

  private static ResultActions compare(ResultActions resultActions, String prefix, Pharmacy pharmacy) throws Exception {
    return resultActions.andExpectAll(
            jsonPath(prefix + ".id").value(pharmacy.getId()),
            jsonPath(prefix + ".name").value(pharmacy.getName()),
            jsonPath(prefix + ".address").value(pharmacy.getAddress()),
            jsonPath(prefix + ".work_time").value(pharmacy.getWorkTime()),
            jsonPath(prefix + ".phone").value(pharmacy.getPhone()));
  }

  @Test
  @SneakyThrows
  void testGetAll() {
    when(pharmacyService.getAll()).thenReturn(pharmacies);
    ResultActions result = mockMvc
            .perform(get("/api/pharmacy/all"))
            .andExpectAll(status().isOk(),
                    jsonPath("$", hasSize(2)));
    compare(result, "$[0]", firstPharmacy);
    compare(result, "$[1]", secondPharmacy);
  }

  @Test
  @SneakyThrows
  void testGetAllPharmaciesByItemId() {
    Long itemId = 1L;
    when(pharmacyService.getAllByItemId(itemId)).thenReturn(pharmacies);
    ResultActions result = mockMvc
            .perform(get("/api/pharmacy/item?item_id=1"))
            .andExpectAll(status().isOk(),
                    jsonPath("$", hasSize(2)));
    compare(result, "$[0]", firstPharmacy);
    compare(result, "$[1]", secondPharmacy);
  }
}
