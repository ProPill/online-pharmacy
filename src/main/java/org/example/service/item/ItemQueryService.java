package org.example.service.item;

import static org.example.exception.TypicalServerExceptions.SPECIALITY_NOT_FOUND;
import static org.example.exception.TypicalServerExceptions.TYPE_NOT_FOUND;
import static org.example.exception.TypicalServerExceptions.USER_NOT_DOC;
import static org.example.exception.TypicalServerExceptions.USER_NOT_FOUND;
import static org.example.resources.Strings.common;
import static org.example.resources.Strings.receipt;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Speciality;
import org.example.entities.user.UserAccount;
import org.example.repository.item.ItemRepository;
import org.example.repository.item.TypeRepository;
import org.example.repository.user.SpecialityRepository;
import org.example.repository.user.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemQueryService {

  private final ItemRepository itemRepository;
  private final TypeRepository typeRepository;
  private final UserAccountRepository userAccountRepository;
  private final SpecialityRepository specialityRepository;

  @Transactional
  public List<Item> getAll() {
    return itemRepository.findAll();
  }

  @Transactional
  public List<Item> getAllReceiptAndNot() {
    List<Item> list = new ArrayList<>();

    Optional<Type> commonType = typeRepository.findByName(common);
    Optional<Type> receiptType = typeRepository.findByName(receipt);

    commonType.ifPresent(type -> list.addAll(type.getItems()));
    receiptType.ifPresent(type -> list.addAll(type.getItems()));

    return list;
  }

  @Transactional
  public List<Item> getAllItemsByDocId(Long id) {
    Optional<UserAccount> user = userAccountRepository.findById(id);
    if (user.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    Speciality spec = user.get().getSpeciality();
    if (spec == null) {
      USER_NOT_DOC.throwException();
    }
    List<Item> list = new ArrayList<>();
    list.addAll(Objects.requireNonNull(user.get().getSpeciality()).getItems());
    list.addAll(getAllReceiptAndNot());
    return list;
  }

  @Transactional
  public List<Item> getAllItemsByTypeId(Long id) {
    Optional<Type> type = typeRepository.findById(id);
    if (type.isEmpty()) {
      TYPE_NOT_FOUND.throwException();
    }
    return type.get().getItems();
  }

  @Transactional
  public List<Item> getAllItemsBySpecId(Long id) {
    Optional<Speciality> spec = specialityRepository.findById(id);
    if (spec.isEmpty()) {
      SPECIALITY_NOT_FOUND.throwException();
    }
    return spec.get().getItems();
  }
}
