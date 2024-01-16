package org.example.controller.cart;

import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.service.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Корзина")
public class CartController extends BaseController {
  private final CartService cartService;

  @Operation(
          summary = "Получение всех товаров в корзине пользователя",
          description = "Получение всех товаров в корзине по id пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/{user_id}")
  public ResponseEntity<?> getAllItemsInCart(@PathVariable(value = "user_id") Long userId) {
    return ResponseEntity.ok(cartService.getUserCart(userId));
  }

  @Operation(
          summary = "Получение количества товара в корзине пользователя",
          description = "Получение количества товара в корзине пользователя по id товара и id пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/quantity_info")
  public ResponseEntity<?> getItemQuantityInCart(
      @RequestParam(value = "item_id") Long itemId, @RequestParam(value = "user_id") Long userId) {
    return ResponseEntity.ok(cartService.getItemsAmountInUsersCart(userId, itemId));
  }

  @Operation(
          summary = "Добавление товара в корзину",
          description = "Добавление товара в корзину по id товара и id пользователя, count по умолчанию 1")
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/add")
  public ResponseEntity<?> addItemToCart(
      @RequestParam(value = "item_id") Long itemId,
      @RequestParam(value = "user_id") Long userId,
      @RequestParam(value = "count") Optional<Long> count) {
    return ResponseEntity.ok(
        cartService.addItemToCart(userId, itemId, count.isPresent() ? count.get() : 1));
  }

  @Operation(
          summary = "Удаление товара из корзины",
          description = "Удаление товара из корзины по id товара и id пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @DeleteMapping("/delete")
  public ResponseEntity<?> deleteItemFromCart(
      @RequestParam(value = "item_id") Long itemId, @RequestParam(value = "user_id") Long userId) {
    return ResponseEntity.ok(cartService.deleteItemFromCart(userId, itemId));
  }

  @Operation(
          summary = "Проверка корзины на наличие рецептурного препарата",
          description = "Проверка корзины на наличие рецептурного препарата по списку id товаров")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/check_receipt")
  public Boolean isCartHaveReceiptItem(@RequestParam(value = "items") Long[] items) {
    return cartService.isCartHaveReceiptItem(items);
  }
}
