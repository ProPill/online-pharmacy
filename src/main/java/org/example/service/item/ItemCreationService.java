package org.example.service.item;

import lombok.RequiredArgsConstructor;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.repository.item.ItemRepository;
import org.example.repository.item.TypeRepository;
import org.example.repository.user.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.exception.TypicalServerExceptions.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemCreationService {

    private final ItemRepository itemRepository;
    private final TypeRepository typeRepository;
    private final SpecialityRepository specialityRepository;

    public Item addItem(String name, Double price, String manufacturer, String pictureUrl, Long typeId, Long specialityId) {
        Optional<Type> type = typeRepository.findById(typeId);
        Optional<Speciality> spec = specialityRepository.findById(specialityId);
        if (type.isEmpty()) {
            NOT_FOUND.throwException(); // TODO
        }
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setManufacturer(manufacturer);
        item.setPictureUrl(pictureUrl);
        item.setType(type.get());
        spec.ifPresent(item::setSpeciality);
        itemRepository.save(item);
        return item;
    }
}
