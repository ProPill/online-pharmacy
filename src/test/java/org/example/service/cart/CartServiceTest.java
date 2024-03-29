package org.example.service.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.example.dto.cart.CartDto;
import org.example.dto.item.ItemDto;
import org.example.dto.item.TypeDto;
import org.example.dto.user.SpecialityDto;
import org.example.entities.cart.Cart;
import org.example.entities.cart.CartToItem;
import org.example.entities.item.Item;
import org.example.entities.item.Type;
import org.example.entities.user.Role;
import org.example.entities.user.Speciality;
import org.example.entities.user.UserAccount;
import org.example.exception.ServerException;
import org.example.repository.cart.CartRepository;
import org.example.repository.cart.CartToItemRepository;
import org.example.repository.item.ItemRepository;
import org.example.repository.user.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
  @Mock private CartRepository cartRepository;
  @Mock private CartToItemRepository cartToItemRepository;
  @Mock private ItemRepository itemRepository;
  @Mock private UserAccountRepository userAccountRepository;

  @InjectMocks private CartService cartService;

  private Cart expectedCart;
  private UserAccount expectedUser;
  private Item expectedItem;
  private CartToItem expectedItemToCart;
  private CartDto expectedCartDto;

  private Item receiptItem;

  MessageDigest digest;

  {
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  void setUp() {
    Type type = new Type();
    type.setId(1L);
    type.setName("Test Type");

    Role role = new Role();
    role.setId(1L);
    role.setName("Test Role");

    Speciality speciality = new Speciality();
    speciality.setId(1L);
    speciality.setName("Test Speciality");

    expectedUser = new UserAccount();
    expectedUser.setId(1L);
    expectedUser.setRole(role);
    expectedUser.setPhone("+79290367458");
    expectedUser.setSpeciality(speciality);
    expectedUser.setFullName("Test FIO");
    expectedUser.setPasswordHash(digest.digest("123456".getBytes(StandardCharsets.UTF_8)));

    expectedCart = new Cart();
    expectedCart.setId(1L);
    expectedCart.setUserAccount(expectedUser);

    expectedCartDto = CartDto.fromCart(expectedCart);

    expectedItem = new Item();
    expectedItem.setId(1L);
    expectedItem.setName("Test Item");
    expectedItem.setPrice(100.0);
    expectedItem.setManufacturer("Test Manufacturer");
    expectedItem.setInfo("Test Info");
    expectedItem.setPictureUrl("test.jpg");
    expectedItem.setType(type);
    expectedItem.setSpeciality(speciality);

    expectedItemToCart = new CartToItem();
    expectedItemToCart.setId(1L);
    expectedItemToCart.setCart(expectedCart);
    expectedItemToCart.setQuantity(5);
    expectedItemToCart.setItem(expectedItem);

    Type receiptType = new Type();
    receiptType.setId(-2L);
    receiptType.setName("receipt");

    receiptItem = new Item();
    receiptItem.setId(2L);
    receiptItem.setName("Test Item");
    receiptItem.setPrice(100.0);
    receiptItem.setManufacturer("Test Manufacturer");
    receiptItem.setInfo("Test Info");
    receiptItem.setPictureUrl("test.jpg");
    receiptItem.setType(receiptType);
    receiptItem.setSpeciality(speciality);
  }

  @Test
  void addUserCart() {
    when(userAccountRepository.findById(anyLong())).thenReturn(Optional.of(expectedUser));

    Cart actualCart = cartService.addUserCart(1L);
    expectedCart.setId(null);

    verify(cartRepository).save(actualCart);

    assertEquals(expectedCart.getId(), actualCart.getId());
    assertEquals(expectedCart.getUserAccount(), actualCart.getUserAccount());
  }

  @Test
  void getUserCartNotEmpty() {
    when(cartRepository.findByUserAccount_Id(anyLong())).thenReturn(Optional.of(expectedCart));

    CartDto actualCartDto = cartService.getUserCart(1L);

    assertEquals(expectedCartDto.user_id(), actualCartDto.user_id());
    assertEquals(expectedCartDto.id(), actualCartDto.id());
  }

  @Test
  void getUserCartEmpty() {
    when(cartRepository.findByUserAccount_Id(anyLong())).thenReturn(Optional.empty());

    try {
      cartService.getUserCart(1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("USER_NOT_FOUND", e.getCode());
      assertEquals("USER_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void getItemsAmountInUsersCartUserNotFound() {
    when(cartRepository.findByUserAccount_Id(anyLong())).thenReturn(Optional.empty());

    try {
      cartService.getItemsAmountInUsersCart(1L, 1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("USER_NOT_FOUND", e.getCode());
      assertEquals("USER_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void getItemsAmountInUsersCartUserItemNotFound() {
    when(cartRepository.findByUserAccount_Id(anyLong())).thenReturn(Optional.of(expectedCart));
    when(cartToItemRepository.getCartToItemByItem_Id(anyLong())).thenReturn(Optional.empty());

    try {
      cartService.getItemsAmountInUsersCart(1L, 1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("NOT_FOUND", e.getCode());
      assertEquals("NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void getItemsAmountInUsersCartUser() {
    when(cartRepository.findByUserAccount_Id(anyLong())).thenReturn(Optional.of(expectedCart));
    when(cartToItemRepository.getCartToItemByItem_Id(anyLong()))
        .thenReturn(Optional.of(expectedItemToCart));

    Long actualAmount = cartService.getItemsAmountInUsersCart(1L, 1L);
    assertEquals(expectedItemToCart.getQuantity().longValue(), actualAmount);
  }

  @Test
  void addItemToCartUserNotFound() {
    when(cartRepository.findByUserAccount_Id(anyLong())).thenReturn(Optional.empty());

    try {
      cartService.addItemToCart(1L, 1L, 1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("USER_NOT_FOUND", e.getCode());
      assertEquals("USER_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void addItemToCartItemNotFound() {
    when(cartRepository.findByUserAccount_Id(anyLong())).thenReturn(Optional.of(expectedCart));
    when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

    try {
      cartService.addItemToCart(1L, 1L, 1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("NOT_FOUND", e.getCode());
      assertEquals("NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void addItemToCartItemIncreaseAmount() {
    when(cartRepository.findByUserAccount_Id(1L)).thenReturn(Optional.of(expectedCart));
    when(itemRepository.findById(1L)).thenReturn(Optional.of(expectedItem));
    when(cartToItemRepository.findByItem_IdAndCart_Id(1L, 1L))
        .thenReturn(Optional.of(expectedItemToCart));
    when(cartToItemRepository.save(expectedItemToCart)).thenReturn(expectedItemToCart);

    expectedItemToCart.setQuantity(expectedItemToCart.getQuantity() + 1);

    ItemDto actualItem = cartService.addItemToCart(1L, 1L, 1L);
    assertEquals(expectedItem.getId(), actualItem.id());
    assertEquals(expectedItem.getName(), actualItem.name());
    assertEquals(expectedItem.getManufacturer(), actualItem.manufacturer());
    assertEquals(
        SpecialityDto.fromSpeciality(expectedItem.getSpeciality()), actualItem.speciality());
    assertEquals(expectedItem.getPictureUrl(), actualItem.pictureUrl());
    assertEquals(TypeDto.fromType(expectedItem.getType()), actualItem.typeId());

    verify(cartToItemRepository).save(expectedItemToCart);
  }

  @Test
  void addNewItemToCartItem() {
    when(cartRepository.findByUserAccount_Id(1L)).thenReturn(Optional.of(expectedCart));
    when(itemRepository.findById(1L)).thenReturn(Optional.of(expectedItem));
    when(cartToItemRepository.findByItem_IdAndCart_Id(1L, 1L)).thenReturn(Optional.empty());
    when(cartToItemRepository.save(any())).thenReturn(expectedItemToCart);

    expectedItemToCart.setId(null);

    cartService.addItemToCart(1L, 1L, 1L);
  }

  @Test
  void deleteItemFromCartUserNotFound() {
    when(cartRepository.findByUserAccount_Id(1L)).thenReturn(Optional.empty());
    try {
      cartService.deleteItemFromCart(1L, 1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("USER_NOT_FOUND", e.getCode());
      assertEquals("USER_NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void deleteItemFromCartItemNotFound() {
    when(cartRepository.findByUserAccount_Id(1L)).thenReturn(Optional.of(expectedCart));
    when(cartToItemRepository.findByItem_IdAndCart_Id(1L, 1L)).thenReturn(Optional.empty());
    try {
      cartService.deleteItemFromCart(1L, 1L);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("NOT_FOUND", e.getCode());
      assertEquals("NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void deleteItemFromCartItem() {
    when(cartRepository.findByUserAccount_Id(1L)).thenReturn(Optional.of(expectedCart));
    when(cartToItemRepository.findByItem_IdAndCart_Id(1L, 1L))
        .thenReturn(Optional.of(expectedItemToCart));

    ItemDto actualItem = cartService.deleteItemFromCart(1L, 1L);
    assertEquals(expectedItem.getId(), actualItem.id());
    assertEquals(expectedItem.getName(), actualItem.name());
    assertEquals(expectedItem.getManufacturer(), actualItem.manufacturer());
    assertEquals(
        SpecialityDto.fromSpeciality(expectedItem.getSpeciality()), actualItem.speciality());
    assertEquals(expectedItem.getPictureUrl(), actualItem.pictureUrl());
    assertEquals(TypeDto.fromType(expectedItem.getType()), actualItem.typeId());
  }

  @Test
  void isCartHaveReceiptItemNotFound() {
    when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

    try {
      Long[] array = {1L, 1L};
      cartService.isCartHaveReceiptItem(array);
    } catch (ServerException e) {
      assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
      assertEquals("NOT_FOUND", e.getCode());
      assertEquals("NOT_FOUND", e.getMessage());
    }
  }

  @Test
  void isCartHaveReceiptItemFalse() {
    when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expectedItem));

    Long[] array = {1L, 1L};
    boolean actualResult = cartService.isCartHaveReceiptItem(array);
    assertFalse(actualResult);
  }

  @Test
  void isCartHaveReceiptItemTrue() {
    when(itemRepository.findById(anyLong())).thenReturn(Optional.of(receiptItem));

    Long[] array = {1L, 1L};
    boolean actualResult = cartService.isCartHaveReceiptItem(array);
    assertTrue(actualResult);
  }
}
