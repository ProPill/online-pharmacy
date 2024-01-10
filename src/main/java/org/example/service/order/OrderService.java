package org.example.service.order;

import static org.example.exception.TypicalServerExceptions.*;

import java.sql.Date;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.example.entities.order.OrderToItem;
import org.example.entities.order.Orders;
import org.example.entities.pharmacy.Pharmacy;
import org.example.entities.user.UserAccount;
import org.example.repository.item.ItemRepository;
import org.example.repository.order.OrderToItemRepository;
import org.example.repository.order.OrdersRepository;
import org.example.repository.pharmacy.PharmacyRepository;
import org.example.repository.user.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrdersRepository ordersRepository;
  private final UserAccountRepository userAccountRepository;
  private final PharmacyRepository pharmacyRepository;
  private final ItemRepository itemRepository;
  private final OrderToItemRepository orderToItemRepository;

  public List<Orders> getAllUserOrders(Long id) {
    Optional<UserAccount> user = userAccountRepository.findById(id);
    if (user.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    List<Orders> orders = user.get().getOrders();
    orders.sort((o1, o2) -> o2.getCreationDate().compareTo(o1.getCreationDate()));
    return orders;
  }

  public Orders placeOrder(
      Long userId,
      Date creationDate,
      Date deliveryDate,
      Double sumPrice,
      Long pharmacyId,
      Long[] items) {
    Optional<UserAccount> user = userAccountRepository.findById(userId);
    if (user.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    Optional<Pharmacy> pharmacy = pharmacyRepository.findById(pharmacyId);
    if (pharmacy.isEmpty()) {
      PHARMACY_NOT_FOUND.throwException();
    }
    OrderToItem otm = new OrderToItem();
    Orders order = new Orders();
    order.setCreationDate(creationDate);
    order.setDeliveryDate(deliveryDate);
    order.setSumPrice(sumPrice);
    order.setUserAccount(user.get());
    order.setPharmacy(pharmacy.get());
    ordersRepository.save(order);
    Map<Long, Integer> itemToQuantity = new HashMap<>();
    for (Long item : items) {
      if (itemToQuantity.containsKey(item)) {
        itemToQuantity.put(item, itemToQuantity.get(item) + 1);
      } else {
        itemToQuantity.put(item, 1);
      }
    }
    for (Long item : itemToQuantity.keySet()) {
      otm.setOrders(order);
      if (itemRepository.findById(item).isEmpty()) {
        ITEM_NOT_FOUND.throwException();
      }
      otm.setItem(itemRepository.findById(item).get());
      otm.setQuantity(itemToQuantity.get(item));
    }
    orderToItemRepository.save(otm);
    return order;
  }
}
