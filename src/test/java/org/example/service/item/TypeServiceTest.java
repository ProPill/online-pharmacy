package org.example.service.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.example.entities.item.Type;
import org.example.repository.item.TypeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TypeServiceTest {

  @Mock private TypeRepository typeRepository;

  @InjectMocks private TypeService typeService;

  @Test
  void getAll() {
    List<Type> expectedTypes =
        Arrays.asList(
            new Type(1L,
                "Type1",
                new ArrayList<>()),
            new Type(2L,
                "Type2",
                new ArrayList<>()));
    when(typeRepository.findAll()).thenReturn(expectedTypes);
    List<Type> result = typeService.getAll();
    assertEquals(2, result.size());
    assertEquals("Type1", result.get(0).getName());
    assertEquals("Type2", result.get(1).getName());
  }
}
