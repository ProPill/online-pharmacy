package org.example.service.item;

import lombok.RequiredArgsConstructor;
import org.example.entities.item.Item;
import org.example.repository.item.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.exception.TypicalServerExceptions.ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemInfoService {

    private final ItemRepository itemRepository;

    public Item getItemInfo(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty()) {
            ITEM_NOT_FOUND.throwException();
        }
        return item.get();
    }
}
