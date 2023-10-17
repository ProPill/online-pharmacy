package org.example.controller.item;

import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.item.TypeDto;
import org.example.service.item.TypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/type")
@RequiredArgsConstructor
public class TypeController extends BaseController {

  private static TypeService typeService;

  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(typeService.getAll().stream().map(TypeDto::fromType).toList());
  }
}
