package org.example.controller.item;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.ItemDto;
import org.example.service.item.ItemSearchService;
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
public class ItemSearchController extends BaseController {

  private final ItemSearchService itemSearchService;

  @Operation(summary = "Поиск товаров", description = "Поиск товаров по названию")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/search_result")
  public ResponseEntity<?> searchItems(@RequestParam(value = "search") String search) {
    return ResponseEntity.ok(
        itemSearchService.searchItemByName(search).stream().map(ItemDto::fromItem).toList());
  }
}
