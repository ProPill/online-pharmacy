package org.example.controller.pharmacy;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.pharmacy.PharmacyDto;
import org.example.service.pharmacy.PharmacyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
@Tag(name = "Аптека")
public class PharmacyController extends BaseController {

  private final PharmacyService pharmacyService;

  @Operation(
      summary = "Получение всех аптек",
      description = "Получение списка всех существующих аптек")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(
        pharmacyService.getAll().stream().map(PharmacyDto::fromPharmacy).toList());
  }

  @Operation(
      summary = "Получение всех аптек, в которых есть товар",
      description = "Получение списка всех аптек, в которых есть товар по id товара")
  @CrossOrigin(origins = "http://localhost:4200")
  @GetMapping("/item")
  public ResponseEntity<?> getAllPharmaciesByItemId(@RequestParam(value = "item_id") Long itemId) {
    return ResponseEntity.ok(
        pharmacyService.getAllByItemId(itemId).stream().map(PharmacyDto::fromPharmacy).toList());
  }
}
