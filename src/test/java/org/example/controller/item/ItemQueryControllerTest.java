package org.example.controller.item;

import static org.example.controller.TestObjects.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.SneakyThrows;
import org.example.controller.MvcUtil;
import org.example.dto.item.ItemDto;
import org.example.dto.user.UserAccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ItemQueryControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MvcUtil mvcUtil;

  @Test
  @SneakyThrows
  void getAll() {
    ResultActions result =
        mockMvc
            .perform(get("/api/item/all"))
            .andExpectAll(status().isOk(), jsonPath("$", hasSize(2)));
    ItemDto[] resultDto = mvcUtil.readResponseValue(ItemDto[].class, result);
    assertArrayEquals(items, resultDto);
  }

  @Test
  @SneakyThrows
  void getAllReceiptAndNot() {
    ResultActions result =
        mockMvc
            .perform(get("/api/item/normal/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    ItemDto[] resultDto = mvcUtil.readResponseValue(ItemDto[].class, result);
    assertArrayEquals(new ItemDto[] {receipt}, resultDto);
  }

  @Test
  @SneakyThrows
  void getAllItemsByDocId() {
    ResultActions result =
        mockMvc
            .perform(get("/api/item/doc/all?user_id=-2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    ItemDto[] resultDto = mvcUtil.readResponseValue(ItemDto[].class, result);
    assertArrayEquals(new ItemDto[] {special, receipt}, resultDto);
  }

  @Test
  @SneakyThrows
  void getAllItemsByTypeId() {
    ResultActions result =
        mockMvc
            .perform(get("/api/item/type?type_id=-2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    ItemDto[] resultDto = mvcUtil.readResponseValue(ItemDto[].class, result);
    assertArrayEquals(new ItemDto[] {receipt}, resultDto);
  }

  @Test
  @SneakyThrows
  void getAllItemsBySpecId() {
    ResultActions result =
        mockMvc
            .perform(get("/api/item/type/category?speciality_id=-1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    ItemDto[] resultDto = mvcUtil.readResponseValue(ItemDto[].class, result);
    assertArrayEquals(new ItemDto[] {special}, resultDto);
  }

  // аутентификация пользователя фармацевта и просмотр всех препаратов
  @Test
  @SneakyThrows
  void getAllItemsByDoc() {
    ResultActions result =
        mockMvc
            .perform(
                post("/api/accounts/login")
                    .param("phone", doctor.phone())
                    .param("password", "654321"))
            .andExpect(status().isOk());
    UserAccountDto resultDto = mvcUtil.readResponseValue(UserAccountDto.class, result);
    assertEquals(doctor, resultDto);

    ResultActions resultItem =
        mockMvc
            .perform(get("/api/item/doc/all?user_id=" + resultDto.id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)));
    ItemDto[] resultItemDto = mvcUtil.readResponseValue(ItemDto[].class, resultItem);
    assertArrayEquals(new ItemDto[] {special, receipt}, resultItemDto);
  }

  // аутентификация пользователя фармацевта и просмотр всех препаратов
  @Test
  @SneakyThrows
  void getAllItemsBySpec() {
    ResultActions result =
        mockMvc
            .perform(
                post("/api/accounts/login")
                    .param("phone", doctor.phone())
                    .param("password", "654321"))
            .andExpect(status().isOk());
    UserAccountDto resultDto = mvcUtil.readResponseValue(UserAccountDto.class, result);
    assertEquals(doctor, resultDto);
    ResultActions resultItem =
        mockMvc
            .perform(get("/api/item/type/category?speciality_id=" + resultDto.speciality().id()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));
    ItemDto[] resultItemDto = mvcUtil.readResponseValue(ItemDto[].class, resultItem);
    assertArrayEquals(new ItemDto[] {special}, resultItemDto);
  }
}
