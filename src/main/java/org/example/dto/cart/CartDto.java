package org.example.dto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.example.entities.cart.Cart;

public record CartDto(
    @JsonProperty("id") Long id,
    @JsonProperty("user_id") Long user_id,
    @JsonProperty("items") List<CartToItemDto> items) {
    public static CartDto fromCart(Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getUserAccount().getId(),
                cart.getCartToItems().stream().map(CartToItemDto::fromCartToItem).toList()
        );
    }
}
