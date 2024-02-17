package org.example.service.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.entities.order.Orders;
import org.example.entities.pharmacy.Pharmacy;
import org.example.entities.user.UserAccount;
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

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

  @Mock private OrdersRepository ordersRepository;

  @Mock private UserAccountRepository userAccountRepository;

  @Mock private PharmacyRepository pharmacyRepository;

  @Mock private PharmacyToItemRepository pharmacyToItemRepository;

  @Mock private ItemRepository itemRepository;

  @Mock private OrderToItemRepository orderToItemRepository;

  @Mock private CartService cartService;

  @InjectMocks private OrderService orderService;

  @Test
  void getAllUserOrders() {
    UserAccount user = new UserAccount();
    when(userAccountRepository.findById(1L)).thenReturn(Optional.of(user));
    List<Orders> userOrders = Arrays.asList(
        new Orders(1L,
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

  }

  @Test
  void placeOrder() {}

  @Test
  void placeOrder_UserNotFound() {}

  @Test
  void placeOrder_PharmacyNotFound() {}

  @Test
  void placeOrder_ItemNotFound() {}

}
