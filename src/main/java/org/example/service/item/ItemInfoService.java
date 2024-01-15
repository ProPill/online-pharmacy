package org.example.service.item;

import static org.example.exception.TypicalServerExceptions.ITEM_NOT_FOUND;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.entities.item.Item;
import org.example.repository.item.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

  private final ItemRepository itemRepository;

  @Transactional
  public Item getItemInfo(Long id) {
    Optional<Item> item = itemRepository.findById(id);
    if (item.isEmpty()) {
      ITEM_NOT_FOUND.throwException();
    }
    return item.get();
  }
}
