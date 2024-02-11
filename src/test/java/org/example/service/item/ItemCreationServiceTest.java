package org.example.service.item;

import org.example.repository.item.ItemRepository;
import org.example.repository.item.TypeRepository;
import org.example.repository.user.SpecialityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemCreationServiceTest {

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private TypeRepository typeRepository;

  @Mock
  private SpecialityRepository specialityRepository;

  @InjectMocks
  private ItemCreationService itemCreationService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void addItem() {
  }

  @Test
  void addItem_specialityIdIsNull() {
  }

  @Test
  void addItem_typeNotFound() {
  }

  @Test
  void addItem_invalidPrice() {
  }

  @Test
  void addItem_invalidNameLength() {
  }

  @Test
  void addItem_invalidManufacturerLength() {
  }

  @Test
  void addItem_invalidInfoLength() {
  }
}