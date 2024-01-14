package org.example.controller.cart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.service.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController extends BaseController {
  private final CartService cartService;

  @CrossOrigin
  @GetMapping("/{user_id}")
  public ResponseEntity<?> getAllItemsInCart(@PathVariable(value = "user_id") Long userId) {
    return ResponseEntity.ok(cartService.getUserCart(userId));
  }

  @CrossOrigin
  @GetMapping("/quantity_info")
  public ResponseEntity<?> getItemQuantityInCart(
      @RequestParam(value = "item_id") Long itemId, @RequestParam(value = "user_id") Long userId) {
    return ResponseEntity.ok(cartService.getItemsAmountInUsersCart(userId, itemId));
  }

  @CrossOrigin
  @PostMapping("/add")
  public ResponseEntity<?> addItemToCart(
      @RequestParam(value = "item_id") Long itemId,
      @RequestParam(value = "user_id") Long userId,
      @RequestParam(value = "count") Optional<Long> count) {
    return ResponseEntity.ok(
        cartService.addItemToCart(userId, itemId, count.isPresent() ? count.get() : 1));
  }

  @CrossOrigin
  @DeleteMapping("/delete")
  public ResponseEntity<?> deleteItemFromCart(
      @RequestParam(value = "item_id") Long itemId, @RequestParam(value = "user_id") Long userId) {
    return ResponseEntity.ok(cartService.deleteItemFromCart(userId, itemId));
  }
}
