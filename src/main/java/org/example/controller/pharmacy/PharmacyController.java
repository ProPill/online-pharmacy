package org.example.controller.pharmacy;

import lombok.RequiredArgsConstructor;
import org.example.controller.BaseController;
import org.example.dto.pharmacy.PharmacyDto;
import org.example.service.pharmacy.PharmacyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pharmacy")
@RequiredArgsConstructor
public class PharmacyController extends BaseController {

  private final PharmacyService pharmacyService;

  @GetMapping("/all")
  public ResponseEntity<?> getAll() {
    return ResponseEntity.ok(
        pharmacyService.getAll().stream().map(PharmacyDto::fromPharmacy).toList());
  }

  @GetMapping("/item")
  public ResponseEntity<?> getAllPharmaciesByItemId(@RequestParam(value = "item_id") Long itemId) {
    return ResponseEntity.ok(
        pharmacyService.getAllByItemId(itemId).stream().map(PharmacyDto::fromPharmacy).toList());
  }
}
