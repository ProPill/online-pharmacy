package org.example.controller.item;

import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.ItemDto;
import org.example.service.item.ItemInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemInfoController extends BaseController {

  private static ItemInfoService itemInfoService;

  @GetMapping("/info/{item_id}")
  public ResponseEntity<?> getItemInfo(@PathVariable(value = "item_id") Long item_id) {
    return ResponseEntity.ok(ItemDto.fromItem(itemInfoService.getItemInfo(item_id)));
  }
}
