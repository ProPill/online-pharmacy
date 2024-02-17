package org.example.service.cart;

import static org.example.exception.TypicalServerExceptions.NOT_FOUND;
import static org.example.exception.TypicalServerExceptions.USER_NOT_FOUND;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.dto.cart.CartDto;
import org.example.dto.item.ItemDto;
import org.example.entities.cart.Cart;
import org.example.entities.cart.CartToItem;
import org.example.entities.item.Item;
import org.example.entities.user.UserAccount;
import org.example.repository.cart.CartRepository;
import org.example.repository.cart.CartToItemRepository;
import org.example.repository.item.ItemRepository;
import org.example.repository.user.UserAccountRepository;
import org.example.resources.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final CartToItemRepository cartToItemRepository;
  private final ItemRepository itemRepository;
  private final UserAccountRepository userAccountRepository;

  @Transactional
  public Cart addUserCart(Long user_id) {
    Optional<UserAccount> user = userAccountRepository.findById(user_id);
    Cart cart = new Cart();
    cart.setUserAccount(user.get());
    cartRepository.save(cart);
    return cart;
  }

  @Transactional
  public CartDto getUserCart(Long user_id) {
    Optional<Cart> cart = cartRepository.findByUserAccount_Id(user_id);
    if (cart.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    return CartDto.fromCart(cart.get());
  }

  @Transactional
  public Long getItemsAmountInUsersCart(Long user_id, Long item_id) {
    Optional<Cart> cart = cartRepository.findByUserAccount_Id(user_id);
    if (cart.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    Optional<CartToItem> item = cartToItemRepository.getCartToItemByItem_Id(item_id);

    if (item.isEmpty()) {
      NOT_FOUND.throwException();
    }

    return item.get().getQuantity().longValue();
  }

  @Transactional
  public ItemDto addItemToCart(Long user_id, Long item_id, Long count) {
    Optional<Cart> cart = cartRepository.findByUserAccount_Id(user_id);
    if (cart.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    CartToItem cartToItem = new CartToItem();

    Optional<Item> item = itemRepository.findById(item_id);

    if (item.isEmpty()) {
      NOT_FOUND.throwException();
    }

    Optional<CartToItem> oldOne =
        cartToItemRepository.findByItem_IdAndCart_Id(item_id, cart.get().getId());
    if (oldOne.isPresent()) {
      cartToItem = oldOne.get();
      cartToItem.setQuantity(cartToItem.getQuantity() + count.intValue());
    } else {
      cartToItem.setItem(item.get());
      cartToItem.setCart(cart.get());
      cartToItem.setQuantity(count.intValue());
    }
    CartToItem addedItem = cartToItemRepository.save(cartToItem);
    return ItemDto.fromItem(addedItem.getItem());
  }

  @Transactional
  public ItemDto deleteItemFromCart(Long user_id, Long item_id) {
    Optional<Cart> cart = cartRepository.findByUserAccount_Id(user_id);
    if (cart.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    Optional<CartToItem> itemToDelete =
        cartToItemRepository.findByItem_IdAndCart_Id(item_id, cart.get().getId());
    if (itemToDelete.isEmpty()) {
      NOT_FOUND.throwException();
    }
    cartToItemRepository.delete(itemToDelete.get());
    return ItemDto.fromItem(itemToDelete.get().getItem());
  }

  @Transactional
  public Boolean isCartHaveReceiptItem(Long[] items) {
    for (Long item : items) {
      Optional<Item> checkItem = itemRepository.findById(item);
      if (checkItem.isEmpty()) {
        NOT_FOUND.throwException();
      } else {
        if (checkItem.get().getType().getName().equals(Strings.receipt)) {
          return true;
        }
      }
    }
    return false;
  }
}
