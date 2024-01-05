package org.example.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.dto.item.ItemDto;
import org.example.entities.cart.CartToItem;

public record CartToItemDto(
    @JsonProperty("item") ItemDto item, @JsonProperty("quantity") Integer quantity) {
  public static CartToItemDto fromCartToItem(CartToItem cartToItem) {
    return new CartToItemDto(ItemDto.fromItem(cartToItem.getItem()), cartToItem.getQuantity());
  }
}
