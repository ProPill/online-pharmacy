package org.example.service.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.exception.ServerException;
import org.example.repository.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class ItemInfoServiceTest {

  @Spy
  private ItemRepository itemRepository;

  @InjectMocks
  private ItemInfoService itemInfoService;

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
  void getItemInfo() {
    when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expectedItem));

    Item actualItem = itemInfoService.getItemInfo(1L);

    assertEquals(expectedItem.getId(), actualItem.getId());
    assertEquals(expectedItem.getName(), actualItem.getName());
    assertEquals(expectedItem.getPrice(), actualItem.getPrice());
    assertEquals(expectedItem.getManufacturer(), actualItem.getManufacturer());
    assertEquals(expectedItem.getInfo(), actualItem.getInfo());
    assertEquals(expectedItem.getPictureUrl(), actualItem.getPictureUrl());
    assertEquals(expectedItem.getType().getId(), actualItem.getType().getId());
    assertEquals(expectedItem.getType().getName(), actualItem.getType().getName());
    assertEquals(expectedItem.getSpeciality().getId(), actualItem.getSpeciality().getId());
    assertEquals(expectedItem.getSpeciality().getName(), actualItem.getSpeciality().getName());
  }

  // TODO - покрыть ITEM_NOT_FOUND.throwException()
  @Test
  void getItemInfo_itemNotFound() {

    Long itemId = 1L;
    when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

    try {
      itemInfoService.getItemInfo(itemId);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("ITEM_NOT_FOUND", e.getCode());
      assertEquals("ITEM_NOT_FOUND", e.getMessage());
    }
  }
}
