package org.example.service.item;

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

  @BeforeEach
  void setUp() {
  }

  @Test
  void searchItemByName() {
  }
}
