package org.example.service.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.entities.user.UserAccount;
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
  void getAll() {
    List<Item> mockItems = Arrays.asList(new Item(), new Item());
    when(itemRepository.findAll()).thenReturn(mockItems);
    List<Item> result = itemQueryService.getAll();
    assertEquals(2, result.size());
  }

  @Test
  void getAllReceiptAndNot() {
    Type commonType = new Type();
    Type receiptType = new Type();
    commonType.setItems(List.of(new Item()));
    receiptType.setItems(List.of(new Item()));
    when(typeRepository.findByName("common")).thenReturn(Optional.of(commonType));
    when(typeRepository.findByName("receipt")).thenReturn(Optional.of(receiptType));
    List<Item> result = itemQueryService.getAllReceiptAndNot();
    assertEquals(2, result.size());
  }

  @Test
  void getAllItemsByDocId() {
    Long userId = 1L;
    UserAccount userAccount = new UserAccount();
    Speciality speciality = new Speciality();
    speciality.setItems(Arrays.asList(new Item(), new Item()));
    userAccount.setSpeciality(speciality);
    when(userAccountRepository.findById(userId)).thenReturn(Optional.of(userAccount));
    when(typeRepository.findByName("common")).thenReturn(Optional.empty());
    when(typeRepository.findByName("receipt")).thenReturn(Optional.empty());
    List<Item> result = itemQueryService.getAllItemsByDocId(userId);
    assertEquals(2, result.size());
  }

  @Test
  void getAllItemsByDocId_userNotFound() {}

  @Test
  void getAllItemsByDocId_userNotDoc() {}

  @Test
  void getAllItemsByTypeId() {
    Long typeId = 1L;
    Type type = new Type();
    type.setItems(Arrays.asList(new Item(), new Item()));
    when(typeRepository.findById(typeId)).thenReturn(Optional.of(type));
    List<Item> result = itemQueryService.getAllItemsByTypeId(typeId);
    assertEquals(2, result.size());
  }

  @Test
  void getAllItemsByTypeId_typeNotFound() {}

  @Test
  void getAllItemsBySpecId() {
    Long specId = 1L;
    Speciality speciality = new Speciality();
    speciality.setItems(Arrays.asList(new Item(), new Item()));
    when(specialityRepository.findById(specId)).thenReturn(Optional.of(speciality));
    List<Item> result = itemQueryService.getAllItemsBySpecId(specId);
    assertEquals(2, result.size());
  }

  @Test
  void getAllItemsBySpecId_specialityNotFound() {}
}
