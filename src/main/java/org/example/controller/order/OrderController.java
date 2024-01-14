package org.example.controller.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.sql.Date;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.order.OrderDto;
import org.example.service.order.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Заказ")
public class OrderController extends BaseController {

  private final OrderService orderService;

  @Operation(
      summary = "Получение всех заказов пользователя",
      description = "Получение всех заказов пользователя по id пользователя")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/orders")
  public ResponseEntity<?> getAllByUserId(@RequestParam(value = "user_id") Long userId) {
    return ResponseEntity.ok(
        orderService.getAllUserOrders(userId).stream().map(OrderDto::fromOrder).toList());
  }

  @Operation(summary = "Разместить заказ", description = "Создание заказа пользователем")
  @CrossOrigin(origins = "http://localhost:4200")
  @PostMapping("/order")
  public ResponseEntity<?> placeOrder(
      @RequestParam(value = "user_id") Long userId,
      @RequestParam(value = "creation_date") Date creationDate,
      @RequestParam(value = "delivery_date") Date deliveryDate,
      @RequestParam(value = "sum_price") Double sumPrice,
      @RequestParam(value = "pharmacy_id") Long pharmacyId,
      @RequestParam(value = "items") Long[] items) {
    return ResponseEntity.ok(
        OrderDto.fromOrder(
            orderService.placeOrder(
                userId, creationDate, deliveryDate, sumPrice, pharmacyId, items)));
  }
}
