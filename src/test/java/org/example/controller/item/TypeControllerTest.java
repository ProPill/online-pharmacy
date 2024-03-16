package org.example.controller.item;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.example.entities.item.Type;
import org.example.entities.pharmacy.Pharmacy;
import org.example.service.item.TypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class TypeControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private TypeService typeService;

  private Type firstType;
  private Type secondType;
  private List<Type> types;
  @BeforeEach
  void setUp() {
    firstType = new Type(1L,"специальный", new ArrayList<>());
    secondType = new Type(2L,"по рецепту", new ArrayList<>());

    types = Arrays.asList(firstType, secondType);
  }

  private static void compare(ResultActions resultActions, String prefix, Type type) throws Exception {
    resultActions.andExpectAll(
            jsonPath(prefix + ".id").value(type.getId()),
            jsonPath(prefix + ".name").value(type.getName()));
  }
  @Test
  @SneakyThrows
  void getAll() {
    when(typeService.getAll()).thenReturn(types);
    ResultActions result= mockMvc
        .perform(get("/api/type/all"))
        .andExpectAll(status().isOk(),
            jsonPath("$", hasSize(2)));
    compare(result, "$[0]", firstType);
    compare(result, "$[1]", secondType);
  }
}
