package org.example.service.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.entities.item.Item;
import org.example.entities.order.Orders;
import org.example.entities.pharmacy.Pharmacy;
import org.example.entities.pharmacy.PharmacyToItem;
import org.example.entities.user.UserAccount;
import org.example.exception.ServerException;
import org.example.repository.item.ItemRepository;
import org.example.repository.order.OrderToItemRepository;
import org.example.repository.order.OrdersRepository;
import org.example.repository.pharmacy.PharmacyRepository;
import org.example.repository.pharmacy.PharmacyToItemRepository;
import org.example.repository.user.UserAccountRepository;
import org.example.service.cart.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock private OrderToItemRepository orderToItemRepository;

  @Mock private CartService cartService;

  @Mock private OrdersRepository ordersRepository;

  @Mock private UserAccountRepository userAccountRepository;

  @Mock private PharmacyRepository pharmacyRepository;

  @Mock private PharmacyToItemRepository pharmacyToItemRepository;

  @Mock private ItemRepository itemRepository;

  @InjectMocks private OrderService orderService;

  @Test
  void getAllUserOrders() {
    UserAccount user = new UserAccount();
    when(userAccountRepository.findById(1L)).thenReturn(Optional.of(user));
    List<Orders> userOrders =
        Arrays.asList(
            new Orders(
                1L,
                user,
                new Date(2024, 1, 1),
                new Date(2024, 1, 5),
                200.0,
                new Pharmacy(),
                new ArrayList<>()),
            new Orders(
                2L,
                user,
                new Date(2024, 2, 1),
                new Date(2024, 2, 5),
                200.0,
                new Pharmacy(),
                new ArrayList<>()));
    user.setOrders(userOrders);
    List<Orders> result = orderService.getAllUserOrders(1L);
    assertEquals(2, result.size());
  }

  @Test
  void getAllUserOrders_UserNotFound() {
    when(userAccountRepository.findById(1L)).thenReturn(Optional.empty());
    try {
      orderService.getAllUserOrders(1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("USER_NOT_FOUND", e.getCode());
      assertEquals("USER_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void placeOrder() {
    UserAccount user = new UserAccount();
    when(userAccountRepository.findById(1L)).thenReturn(Optional.of(user));
    Pharmacy pharmacy = new Pharmacy();
    when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
    Item item1 = new Item();
    when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
    Item item2 = new Item();
    when(itemRepository.findById(2L)).thenReturn(Optional.of(item2));
    Orders order =
        orderService.placeOrder(
            1L, new Date(2024, 2, 1), new Date(2024, 2, 5), 100.0, 1L, new Long[] {1L, 2L});
    verify(userAccountRepository, times(1)).findById(1L);
    verify(pharmacyRepository, times(1)).findById(1L);
    verify(ordersRepository, times(1)).save(order);
  }

  @Test
  void placeOrder_UserNotFound() {
    when(userAccountRepository.findById(1L)).thenReturn(Optional.empty());
    try {
      orderService.placeOrder(
          1L, new Date(2024, 2, 1), new Date(2024, 2, 5), 100.0, 1L, new Long[] {1L, 2L, 3L});
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("USER_NOT_FOUND", e.getCode());
      assertEquals("USER_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void placeOrder_PharmacyNotFound() {
    UserAccount user = new UserAccount();
    when(userAccountRepository.findById(1L)).thenReturn(Optional.of(user));
    when(pharmacyRepository.findById(1L)).thenReturn(Optional.empty());
    try {
      orderService.placeOrder(
          1L, new Date(2024, 2, 1), new Date(2024, 2, 5), 100.0, 1L, new Long[] {1L, 2L, 3L});
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("PHARMACY_NOT_FOUND", e.getCode());
      assertEquals("PHARMACY_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void placeOrder_ItemNotFound() {
    UserAccount user = new UserAccount();
    when(userAccountRepository.findById(1L)).thenReturn(Optional.of(user));
    Pharmacy pharmacy = new Pharmacy();
    when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
    when(itemRepository.findById(1L)).thenReturn(Optional.of(new Item()));
    try {
      orderService.placeOrder(
          1L, new Date(2024, 2, 1), new Date(2024, 2, 5), 100.0, 1L, new Long[] {1L, 2L, 3L});
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("ITEM_NOT_FOUND", e.getCode());
      assertEquals("ITEM_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void testPlaceOrder_ItemFoundInPharmacyToItem() {
    Long itemId = 123L;
    Item item = new Item();
    UserAccount user = new UserAccount();
    when(userAccountRepository.findById(1L)).thenReturn(Optional.of(user));
    Pharmacy pharmacy = new Pharmacy();
    when(pharmacyRepository.findById(1L)).thenReturn(Optional.of(pharmacy));
    when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
    PharmacyToItem pharmacyToItem = new PharmacyToItem();
    pharmacyToItem.setItem(item);
    pharmacyToItem.setQuantity(1);
    when(pharmacyToItemRepository.findAllByPharmacyIdAndItemId(1L, itemId))
        .thenReturn(pharmacyToItem);
    Orders order =
        orderService.placeOrder(
            1L, new Date(2024, 2, 1), new Date(2024, 2, 5), 100.0, 1L, new Long[] {itemId});
    verify(itemRepository, times(4)).findById(itemId);
    verify(pharmacyToItemRepository, times(1)).findAllByPharmacyIdAndItemId(1L, itemId);
    verify(pharmacyToItemRepository, times(1)).save(pharmacyToItem);
  }

  @Test
  void testPlaceOrder_ItemToQuantityMap() {
    Long userId = 1L;
    Item item1 = new Item();
    Item item2 = new Item();
    Date creationDate = new Date(2024, 2, 1);
    Date deliveryDate = new Date(2024, 2, 5);
    Double sumPrice = 100.0;
    Long pharmacyId = 2L;
    Long[] items = new Long[] {1L, 2L, 1L};
    UserAccount user = new UserAccount();
    when(itemRepository.findById(1L)).thenReturn(Optional.of(item1));
    when(itemRepository.findById(2L)).thenReturn(Optional.of(item2));
    when(userAccountRepository.findById(userId)).thenReturn(Optional.of(user));
    Pharmacy pharmacy = new Pharmacy();
    when(pharmacyRepository.findById(pharmacyId)).thenReturn(Optional.of(pharmacy));
    Orders order =
        orderService.placeOrder(userId, creationDate, deliveryDate, sumPrice, pharmacyId, items);
    verify(userAccountRepository, times(1)).findById(userId);
    verify(pharmacyRepository, times(1)).findById(pharmacyId);
    verify(ordersRepository, times(1)).save(order);
  }
}
