package org.example.service.pharmacy;

import static org.example.exception.TypicalServerExceptions.ITEM_NOT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.entities.item.Item;
import org.example.entities.pharmacy.Pharmacy;
import org.example.repository.item.ItemRepository;
import org.example.repository.pharmacy.PharmacyRepository;
import org.example.repository.pharmacy.PharmacyToItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyService {

  private final PharmacyRepository pharmacyRepository;
  private final PharmacyToItemRepository pharmacyToItemRepository;
  private final ItemRepository itemRepository;

  public List<Pharmacy> getAll() {
    return pharmacyRepository.findAll();
  }

  public List<Pharmacy> getAllByItemId(Long id) {
    Optional<Item> item = itemRepository.findById(id);
    if (item.isEmpty()) {
      ITEM_NOT_FOUND.throwException();
    }
    List<Pharmacy> pharmacies = new ArrayList<>();
    item.get().getPharmacyToItems().forEach(it -> pharmacies.add(it.getPharmacy()));
    return pharmacies;
  }
}
