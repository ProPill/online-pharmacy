package org.example.service.item;

import org.example.repository.item.ItemRepository;
import org.example.repository.item.TypeRepository;
import org.example.repository.user.SpecialityRepository;
import org.example.repository.user.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ItemQueryServiceTest {

  @Mock private ItemRepository itemRepository;

  @Mock private TypeRepository typeRepository;

  @Mock private UserAccountRepository userAccountRepository;

  @Mock private SpecialityRepository specialityRepository;

  @InjectMocks private ItemQueryService itemQueryService;

  @BeforeEach
  void setUp() {}

  @Test
  void getAll() {}

  @Test
  void getAllReceiptAndNot() {}

  @Test
  void getAllItemsByDocId() {}

  @Test
  void getAllItemsByDocId_userNotFound() {}

  @Test
  void getAllItemsByDocId_userNotDoc() {}

  @Test
  void getAllItemsByTypeId() {}

  @Test
  void getAllItemsByTypeId_typeNotFound() {}

  @Test
  void getAllItemsBySpecId() {}

  @Test
  void getAllItemsBySpecId_specialityNotFound() {}
}
