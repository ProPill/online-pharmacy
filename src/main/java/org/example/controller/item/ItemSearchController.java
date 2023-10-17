package org.example.controller.item;

import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.ItemDto;
import org.example.service.item.ItemSearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemSearchController extends BaseController {

  private final ItemSearchService itemSearchService;

  @GetMapping("/search_result")
  public ResponseEntity<?> searchItems(@RequestParam(value = "search") String search) {
    return ResponseEntity.ok(
        itemSearchService.searchItemByName(search).stream().map(ItemDto::fromItem).toList());
  }
}
