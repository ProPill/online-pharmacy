package org.example.controller.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.ItemDto;
import org.example.service.item.ItemQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
@Tag(name = "Товар")
public class ItemQueryController extends BaseController {

  private final ItemQueryService itemQueryService;

  @Operation(
      summary = "Получение всех товаров",
      description = "Получение всех существующих товаров")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(itemQueryService.getAll().stream().map(ItemDto::fromItem).toList());
  }

  @Operation(
      summary = "Получение всех товаров доступных для обычных пользователей",
      description =
          "Получение всех товаров(рецептурных и не рецептурных) доступных для обычных"
              + " пользователей")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/normal/all")
  public ResponseEntity<?> getAllReceiptAndNot() {
    return ResponseEntity.ok(
        itemQueryService.getAllReceiptAndNot().stream().map(ItemDto::fromItem).toList());
  }

  @Operation(
      summary = "Получение всех товаров доступных для доктора",
      description =
          "Получение всех товаров(рецептурных, не рецептурных и специальных по профессии доктора) "
              + "доступных для доктора по его id")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/doc/all")
  public ResponseEntity<?> getAllItemsByDocId(@RequestParam(value = "user_id") Long userId) {
    return ResponseEntity.ok(
        itemQueryService.getAllItemsByDocId(userId).stream().map(ItemDto::fromItem).toList());
  }

  @Operation(
      summary = "Получение всех товаров по типу",
      description = "Получение всех товаров по id типа")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/type")
  public ResponseEntity<?> getAllItemsByTypeId(@RequestParam(value = "type_id") Long typeId) {
    return ResponseEntity.ok(
        itemQueryService.getAllItemsByTypeId(typeId).stream().map(ItemDto::fromItem).toList());
  }

  @Operation(
      summary = "Получение всех товаров по специальности врача",
      description = "Получение всех товаров по id специальности врача")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/type/category")
  public ResponseEntity<?> getAllItemsBySpecId(
      @RequestParam(value = "speciality_id") Long specialityId) {
    return ResponseEntity.ok(
        itemQueryService.getAllItemsBySpecId(specialityId).stream()
            .map(ItemDto::fromItem)
            .toList());
  }
}
