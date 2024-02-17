package org.example.service.pharmacy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.pharmacy.Pharmacy;
import org.example.entities.pharmacy.PharmacyToItem;
import org.example.entities.user.Speciality;
import org.example.exception.ServerException;
import org.example.repository.item.ItemRepository;
import org.example.repository.pharmacy.PharmacyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class PharmacyServiceTest {
  @InjectMocks private PharmacyService pharmacyService;
  @Mock private PharmacyRepository pharmacyRepository;

  @Mock private ItemRepository itemRepository;

  private Item expectedItem;
  private List<Pharmacy> pharmacyList;

  @BeforeEach
  void setUp() {
    Pharmacy pharmacy =
        new Pharmacy(1L, "test", "test", "test", "test", new ArrayList<>(), new ArrayList<>());
    List<PharmacyToItem> pharmacyToItem =
        List.of(new PharmacyToItem(1L, pharmacy, expectedItem, 5));
    expectedItem =
        new Item(
            1L,
            "NoDobs",
            1000.0,
            "Rus SPb",
            "some info",
            "nodobs.jpg",
            new Type(),
            new Speciality(),
            new ArrayList<>(),
            new ArrayList<>(),
            pharmacyToItem);

    pharmacyList = List.of(pharmacy);
    expectedItem =
        new Item(
            1L,
            "NoDobs",
            1000.0,
            "Rus SPb",
            "some info",
            "nodobs.jpg",
            new Type(),
            new Speciality(),
            new ArrayList<>(),
            new ArrayList<>(),
            pharmacyToItem);
  }

  @Test
  void getAll() {
    when(pharmacyRepository.findAll()).thenReturn(pharmacyList);

    List<Pharmacy> actualPharmacies = pharmacyService.getAll();

    assertEquals(pharmacyList, actualPharmacies);
  }

  @Test
  void getAllByItemIdItemNotFound() {
    when(itemRepository.findById(any())).thenReturn(Optional.empty());

    ServerException exception =
        assertThrows(ServerException.class, () -> pharmacyService.getAllByItemId(1L));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("ITEM_NOT_FOUND", exception.getCode());
    assertEquals("ITEM_NOT_FOUND", exception.getMessage());
  }

  @Test
  void getAllByItemId() {
    when(itemRepository.findById(any())).thenReturn(Optional.of(expectedItem));

    List<Pharmacy> actualPharmacies = pharmacyService.getAllByItemId(1L);

    assertEquals(pharmacyList, actualPharmacies);
  }
}
