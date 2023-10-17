package org.example.controller.item;

import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.ItemDto;
import org.example.service.item.ItemQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemQueryController extends BaseController {
    private static ItemQueryService itemQueryService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(
    ) {
        return ResponseEntity.ok(
                itemQueryService.getAll().stream().map(ItemDto::fromItem).toList()
        );
    }

    @GetMapping("/normal/all")
    public ResponseEntity<?> getAllReceiptAndNot(
    ) {
        return ResponseEntity.ok(
                itemQueryService.getAllReceiptAndNot().stream().map(ItemDto::fromItem).toList()
        );
    }

    @GetMapping("/doc/all")
    public ResponseEntity<?> getAllItemsByDocId(
            @RequestParam(value = "user_id") Long userId
    ) {
        return ResponseEntity.ok(
                itemQueryService.getAllItemsByDocId(userId).stream().map(ItemDto::fromItem).toList()
        );
    }

    @GetMapping("/type")
    public ResponseEntity<?> getAllItemsByTypeId(
            @RequestParam(value = "type_id") Long typeId
    ) {
        return ResponseEntity.ok(
                itemQueryService.getAllItemsByTypeId(typeId).stream().map(ItemDto::fromItem).toList()
        );
    }

    @GetMapping("/type/category")
    public ResponseEntity<?> getAllItemsBySpecId(
            @RequestParam(value = "speciality_id") Long specialityId
    ) {
        return ResponseEntity.ok(
                itemQueryService.getAllItemsBySpecId(specialityId).stream().map(ItemDto::fromItem).toList()
        );
    }
}
