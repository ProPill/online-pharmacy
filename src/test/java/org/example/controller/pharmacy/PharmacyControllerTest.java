package org.example.controller.pharmacy;

import static org.example.controller.TestObjects.pharmacies;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.example.dto.pharmacy.PharmacyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class PharmacyControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  @Test
  @SneakyThrows
  void testGetAll() {
    ResultActions result =
        mockMvc
            .perform(get("/api/pharmacy/all"))
            .andExpectAll(status().isOk(), jsonPath("$", hasSize(2)));
    PharmacyDto[] resultDto = mvcUtil.readResponseValue(PharmacyDto[].class, result);
    assertArrayEquals(pharmacies, resultDto);
  }

  @Test
  @SneakyThrows
  void testGetAllPharmaciesByItemId() {
    ResultActions result =
        mockMvc
            .perform(get("/api/pharmacy/item?item_id=1"))
            .andExpectAll(status().isOk(), jsonPath("$", hasSize(2)));
    PharmacyDto[] resultDto = mvcUtil.readResponseValue(PharmacyDto[].class, result);
    assertArrayEquals(pharmacies, resultDto);
  }

  @Test
  @SneakyThrows
  void testGetAllPharmaciesByItemId_ItemNotFound() {
    mockMvc
        .perform(get("/api/pharmacy/item?item_id=-1"))
        .andExpectAll(status().isNotFound(), jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.code").value("ITEM_NOT_FOUND"))
        .andExpect(jsonPath("$.status").value(404))
        .andExpect(jsonPath("$.message").value("ITEM_NOT_FOUND"));
  }
}
