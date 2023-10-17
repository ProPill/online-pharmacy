package org.example.controller.order;

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
public class OrderController extends BaseController {

  private final OrderService orderService;

  @GetMapping("/orders")
  public ResponseEntity<?> getAllByUserId(@RequestParam(value = "user_id") Long userId) {
    return ResponseEntity.ok(
        orderService.getAllUserOrders(userId).stream().map(OrderDto::fromOrder).toList());
  }

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
