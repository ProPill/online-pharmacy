package org.example.service.order;

import static org.junit.jupiter.api.Assertions.*;

import org.example.repository.item.ItemRepository;
import org.example.repository.order.OrderToItemRepository;
import org.example.repository.order.OrdersRepository;
import org.example.repository.pharmacy.PharmacyRepository;
import org.example.repository.pharmacy.PharmacyToItemRepository;
import org.example.repository.user.UserAccountRepository;
import org.example.service.cart.CartService;
import org.junit.jupiter.api.BeforeEach;
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

  @BeforeEach
  void setUp() {
  }

  @Test
  void getAllUserOrders() {
  }

  @Test
  void placeOrder() {
  }
}