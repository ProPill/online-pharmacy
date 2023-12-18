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
import org.example.repository.cart.CartRepository;
import org.example.repository.cart.CartToItemRepository;
import org.example.repository.item.ItemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
  private final CartRepository cartRepository;
  private final CartToItemRepository cartToItemRepository;
  private final ItemRepository itemRepository;

  public CartDto getUserCart(Long user_id) {
    Optional<Cart> cart = cartRepository.findByUserAccount_Id(user_id);
    if (cart.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    return CartDto.fromCart(cart.get());
  }

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
    cartToItem.setItem(item.get());
    cartToItem.setCart(cart.get());
    cartToItem.setQuantity(count.intValue());
    CartToItem addedItem = cartToItemRepository.save(cartToItem);
    return ItemDto.fromItem(addedItem.getItem());
  }

  public ItemDto deleteItemFromCart(Long user_id, Long item_id) {
    Optional<Cart> cart = cartRepository.findByUserAccount_Id(user_id);
    if (cart.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    Optional<CartToItem> itemToDelete = cartToItemRepository.findByItem_Id(item_id);

    if (itemToDelete.isEmpty()) {
      NOT_FOUND.throwException();
    }

    cartToItemRepository.delete(itemToDelete.get());
    return ItemDto.fromItem(itemToDelete.get().getItem());
  }
}
