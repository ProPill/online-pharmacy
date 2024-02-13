package org.example.service.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.repository.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemSearchServiceTest {

  @Mock
  private ItemRepository itemRepository;

  @InjectMocks
  private ItemSearchService itemSearchService;

  @Test
  void searchItemByName() {
    List<Item> itemList = Arrays.asList(
        new Item(1L,
            "NoDobs",
            1000.0,
            "Rus SPb",
            "some info",
            "nodobs.jpg",
            new Type(),
            new Speciality(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()),
        new Item(
            2L,
            "antigrippin",
            20.0,
            "Rus",
            "some info",
            "antigrippin.jpg",
            new Type(),
            new Speciality(),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>())
    );
    when(itemRepository.findAll()).thenReturn(itemList);
    List<Item> result = itemSearchService.searchItemByName("No");
    assertEquals(1, result.size());
    assertEquals("NoDobs", result.get(0).getName());
  }

  // TODO - если не будет хватать кол-ва тестов (покрытие 100% даже без них)
  // тут можно чекнуть на пустой список
  // чекнуть на lower/upper case
}
