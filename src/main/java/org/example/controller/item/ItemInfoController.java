package org.example.controller.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.ItemDto;
import org.example.service.item.ItemInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Tag(name = "Товар")
public class ItemInfoController extends BaseController {

  private final ItemInfoService itemInfoService;

  @Operation(
      summary = "Информация о товаре",
      description = "Получение информации о товаре по его id")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/info/{item_id}")
  public ResponseEntity<?> getItemInfo(@PathVariable(value = "item_id") Long itemId) {
    return ResponseEntity.ok(ItemDto.fromItem(itemInfoService.getItemInfo(itemId)));
  }
}
